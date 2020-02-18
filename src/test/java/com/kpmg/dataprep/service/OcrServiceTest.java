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

    @Test
    public void ocrParsingTest(){
        String a = "001 필라이트캔50014Ｌ         1     1,190원";
        String [] pars = makeGoodsInfo(a);
        System.out.println("test:"+pars[0]);
        System.out.println("test:"+pars[1]);
        System.out.println("test:"+pars[2]);
    }

    @Test
    public String[] makeGoodsInfo(String s){
        String name="";
        String num="";
        String price="";
        int numFindStart=0;
        int priceFindStart=0;

        for(int i=4; i <s.length();i++){
            if(s.charAt(i) == ' '){
                name = s.substring(4,i);
                numFindStart = i;
                break;
            }
        }
        for(int i=numFindStart; i<s.length(); i++){
            if(s.charAt(i) != ' '){
                num = String.valueOf(s.charAt(i));
                priceFindStart = i+1;
                break;
            }
        }
        for(int i=priceFindStart; i<s.length(); i++){
            if(s.charAt(i) != ' '){
                price = s.substring(i,s.length()-1);
                break;
            }
        }

        String[] goodsInfo = {name,num,price};
        return goodsInfo;
    }

}