package com.kpmg.dataprep.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class OcrServiceTest {

    @Value("${file.upload.dir}")
    String uploadFileDir;
    @Value("${python.file.dir}")
    String pythonFileDir;

    @Test
    public void readPython() {
        try{
            int imageInfoIndex = 0;
            //파이썬 실행 경로
            String path = "python3"+"\t"+pythonFileDir+"ocr.py"+"\t"+uploadFileDir+"ocrtest.jpg";
            System.out.println("path:"+path);
            String s = null;
            Process p = Runtime.getRuntime().exec(path);
            BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
            while((s = in.readLine()) != null){
                ++imageInfoIndex;
                System.out.println(s);
            }
        }catch (IOException ie){
            ie.printStackTrace();
        }
    }
}