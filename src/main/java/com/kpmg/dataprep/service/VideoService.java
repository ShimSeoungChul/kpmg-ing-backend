package com.kpmg.dataprep.service;

import com.kpmg.dataprep.cmmn.CmmnUtil;
import com.kpmg.dataprep.dao.VideoRepository;
import com.kpmg.dataprep.domain.Ocr;
import com.kpmg.dataprep.domain.Video;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class VideoService {
    String tag = "VideoService";

    @Value("${video.file.dir}")
    String videoFileDir;

    @Autowired
    VideoRepository videoRepository;

    //ffmpeg 인코더를 통해 동영상을 mp3 음성파일로 변환
    public String videoToMp3(String id) throws IOException {
        String result = id+".mp3";
        String path = "ffmpeg -i" + "\t" + videoFileDir + id+".mp4" + "\t" + videoFileDir+ id+".mp3";
        System.out.println("path:" + path);
        Process p = Runtime.getRuntime().exec(path);
        System.out.println("tes1\n");
        BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
        System.out.println("tes2\n");

        return result;
    }

    //네이버 stt api를 이용하여 음성 자막 텍스트 생성
    public String mp3ToText(String mp3FileNm){
        String clientId = "i21sz66fh0";             // Application Client ID";
        String clientSecret = "wsWkqkOsHD6xMEMPdBuJcqgO98ubgB0iSDdi4Kkf";     // Application Client Secret";

        try {
            String imgFile = videoFileDir+mp3FileNm;
            File voiceFile = new File(imgFile);
            StringBuffer response = null;

            String language = "Eng";        // 언어 코드 ( Kor, Jpn, Eng, Chn )
            String apiURL = "https://naveropenapi.apigw.ntruss.com/recog/v1/stt?lang=" + language;
            URL url = new URL(apiURL);

            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setUseCaches(false);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestProperty("Content-Type", "application/octet-stream");
            conn.setRequestProperty("X-NCP-APIGW-API-KEY-ID", clientId);
            conn.setRequestProperty("X-NCP-APIGW-API-KEY", clientSecret);

            OutputStream outputStream = conn.getOutputStream();
            FileInputStream inputStream = new FileInputStream(voiceFile);
            byte[] buffer = new byte[4096];
            int bytesRead = -1;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            outputStream.flush();
            inputStream.close();
            BufferedReader br = null;
            int responseCode = conn.getResponseCode();
            if(responseCode == 200) { // 정상 호출
                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {  // 오류 발생
                System.out.println("error!!!!!!! responseCode= " + responseCode);
                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            }
            String inputLine;

            if(br != null) {
                response = new StringBuffer();
                while ((inputLine = br.readLine()) != null) {
                    response.append(inputLine);
                }
                br.close();
                System.out.println(response.toString());
            } else {
                System.out.println("error !!!");
            }
            return response.toString();
        } catch (Exception e) {
            System.out.println(e);
            return "FAIL";
        }
    }


    public Map<String, String> insert(HttpServletRequest request) {
        Map<String, String> rslt = new HashMap<>();
        try {
            String jsnStr = CmmnUtil.getJsnStr(request);
            String fileId = CmmnUtil.getStrValFromJsnStr(jsnStr, "fileId");
            String workerNm = CmmnUtil.getStrValFromJsnStr(jsnStr, "workerNm");
            String sttInfo = CmmnUtil.getStrValFromJsnStr(jsnStr, "sttInfo");

            Video video = Video.builder()
                    .workerName(workerNm)
                    .videoFileId(fileId+".mp4")
                    .speechFileId(fileId+".mp3")
                    .updateTime(LocalDateTime.now())
                    .videoInfo(sttInfo)
                    .build();

            videoRepository.save(video);
            rslt.put("rslt","SUCC");
            return rslt;
        }catch (Exception e){
            System.out.println(tag+"실패원인:"+e.getMessage()+"\n");
            rslt.put("rslt","FAIL");
            return rslt;
        }
    }

    public int getPoint(String id){
        ArrayList<Video> selected = videoRepository.findByWorkerName(id);
        System.out.println(selected.size());
        int point = 0;
        for (Video tmp : selected) {
            if(tmp.getUpdateTime() != null && !tmp.getUpdateTime().equals("") ){
                point = point+30;
            }
        }
        return point;
    }
}
