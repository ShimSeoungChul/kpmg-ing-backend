package com.kpmg.dataprep.service;

import com.kpmg.dataprep.dao.ImageRepository;
import com.kpmg.dataprep.domain.Image;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@SpringBootTest
public class ImageServiceTest {
    @Autowired
    ImageRepository imageRepository;

    @Value("${file.upload.dir}")
    String uploadFileDir;

    @Value("${python.file.dir}")
    String pythonFileDir;


    @Test
    public void readPython() {
        try{
            String path = "python3"+"\t"+pythonFileDir+"object_classification.py"+"\t"+uploadFileDir+"831cff2ede294c04ba37b75b33d63bd0951.png";
            System.out.println("path:"+path);
            String s = null;
            Process p = Runtime.getRuntime().exec(path);
            BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
            while((s = in.readLine()) != null){
                System.out.println(s);
            }
            System.out.println("\ntesttest");
        }catch (IOException ie){
            ie.printStackTrace();
        }
///Users/simseungcheol/Desktop/studyProject/dataprepfile/831cff2ede294c04ba37b75b33d63bd0951

    }

    @Test
    public  void create(){
        //이미지 정보를 데이터베이스에 저장
        Image saveImage = Image.builder()
                .fileId("testw")
                .workerName("test")
                .build();

        Image saved = imageRepository.save(saveImage);
    }

    @Test
    public Map<String , String> update(){
        Image image = Image.builder()
                .fileId("85d6898b35f54de894823e6e3713f977341")
                .imageName("testest")
                .build();
        
        Optional<Image> findImage = imageRepository.findByFileId(image.getFileId());

        findImage.map(selectImage ->{
            selectImage.setImageName(image.getImageName());
            selectImage.setUpdateTime(LocalDateTime.now());
            Image updated = imageRepository.save(selectImage);
            Map<String , String> map = new HashMap<>();
            if(updated != null){
                return map.put("rslt","SUCC");
            }else{
                return map.put("rslt","FAIL");
            }
        });

        //update 실패시 결과 값에 FAIL 리턴
        Map<String , String> map = new HashMap<>();
        map.put("rslt","FAIL");
        return map;
    }



}