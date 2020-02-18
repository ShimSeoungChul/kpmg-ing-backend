package com.kpmg.dataprep.service;

import com.kpmg.dataprep.cmmn.CmmnUtil;
import com.kpmg.dataprep.dao.FileInfoRepository;
import com.kpmg.dataprep.domain.FileInfo;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.*;
import java.time.LocalDateTime;

@Service
public class FileService {
    String tag = "FileService";

    @Value("${file.upload.dir}")
    String uploadFileDir;

    @Autowired
    FileInfoRepository fileInfoRepository;

//    public File addFile(File file){
//        File saved = fileRepository.save(file);
//        return saved;
//    }

//    //파일을 디렉토리에 저장하고, 파일 이름을 데이터베이스에 입력한다.
//    public String addFile(HttpServletRequest request) throws Exception {
//        Map<String, MultipartFile> files = multiRequest.getFileMap();
//        Iterator<Entry<String, MultipartFile>> iterator = files.entrySet().iterator();
//
//        /** 첨부파일 위치 지정 */
//        MultipartFile multipartFile = null;
//        String originalFileName = null;
//        String storedFileName = null;
//
//        // 파일 업로드 경로
//        String uploadDir = uploadFileDir;
//
//        File file = new File(uploadDir);
//        if (file.exists() == false) {
//            file.mkdirs();
//        }
//
//        while (iterator.hasNext()) {
//
//            Entry<String, MultipartFile> entry = iterator.next();
//            multipartFile = entry.getValue();
//
//            if (multipartFile.isEmpty() == false) {
//                storedFileName = CmmnUtil.getRandomString();
//
//                file = new File(uploadDir + storedFileName);
//                // 지정된 경로에 파일을 생성
//                multipartFile.transferTo(file);
//
////                returnMap = new EgovMap();
////                returnMap.put("storedFileName", storedFileName);
////                returnMap.put("originalFileName", originalFileName);
////                returnMap.put("fileSize", Long.toString(fileSize));
////                returnMap.put("storedPath", uploadDir);
////                returnList.add(returnMap);
//            }
//        }
//        return storedFileName;
//    }

    public FileInfo updateFile(FileInfo fileInfo){

        return fileInfo;
    }

    public FileInfo insertFile(FileInfo fileInfo){
        FileInfo saved = fileInfoRepository.save(fileInfo);
        return fileInfo;
    }

        //파일을 디렉토리에 저장하고, 파일 이름을 데이터베이스에 입력한다.
    public String addFile(HttpServletRequest request) {
        try {
            System.out.println(tag + "addFile");
            String jsnStr = CmmnUtil.getJsnStr(request);
            String stringImage = CmmnUtil.getStrValFromJsnStr(jsnStr, "stringImage");
            String userNm = CmmnUtil.getStrValFromJsnStr(jsnStr, "userNm");

            /*Convert Base64 String to Image File
             *https://www.baeldung.com/java-base64-image-string
             */
            String storedFileName = CmmnUtil.getRandomString();
            System.out.println("파일저장:" + storedFileName);

            byte[] decodedBytes = Base64.decode(stringImage);
            String pathAndName = uploadFileDir + storedFileName;

            BufferedImage imag = ImageIO.read(new ByteArrayInputStream(decodedBytes));
            ImageIO.write(imag, "jpg", new File(pathAndName));

            //파일 정보 데이터베이스에 저장
            FileInfo fileInfo = FileInfo.builder()
                    .FileNm(storedFileName)
                    .storedPath(uploadFileDir)
                    .createId(userNm)
                    .createDt(LocalDateTime.now())
                    .build();
            insertFile(fileInfo);

            //파일 저장 성공
            return storedFileName;
        }catch (Exception e) {//파일 저장 실패
            System.out.println(tag+"실패원인:"+e.getMessage());
            return "FAIL";
        }
    }
}
