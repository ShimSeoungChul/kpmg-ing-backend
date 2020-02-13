package com.kpmg.dataprep.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;


@SpringBootTest
public class ImageServiceTest {

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



}