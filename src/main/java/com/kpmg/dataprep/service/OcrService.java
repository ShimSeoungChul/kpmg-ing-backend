package com.kpmg.dataprep.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

@Service
public class OcrService {
    @Autowired
    FileService fileService;
//    @Autowired
//    OcrRepository ocrRepository;

    @Value("${file.upload.dir}")
    String uploadFileDir;
    @Value("${python.file.dir}")
    String pythonFileDir;

    //파이썬 파일의 머신러닝 코드를 통해 OCR 정보 데이터 생성하기
    public Map<String,String> makeImageInfo(MultipartHttpServletRequest multiRequest) throws Exception {
        //이미지를 파일로 저장
        String filename = fileService.addFile(multiRequest);



        Map<String, String> map = new HashMap<>();
        //파이썬 파일을 통해 이미지 정보 가져와서 변수에 저장하기
        try{
            int imageInfoIndex = 0;
            //파이썬 실행 경로
            String path = "python3"+"\t"+pythonFileDir+"ocr.py"+"\t"+uploadFileDir+"ocrtest.jpg";
            System.out.println("path:"+path);
            String s = null;
            Process p = Runtime.getRuntime().exec(path);
            BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
            while((s = in.readLine()) != null){
                map.put(Integer.toString(imageInfoIndex),s);
                ++imageInfoIndex;
                System.out.println(s);
            }
        }catch (IOException ie){
            ie.printStackTrace();
        }
        //이미지 정보 저장
        return map;
    }
}
