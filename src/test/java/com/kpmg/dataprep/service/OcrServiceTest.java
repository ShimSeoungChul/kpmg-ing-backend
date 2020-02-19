package com.kpmg.dataprep.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class OcrServiceTest {

    @Value("${file.upload.dir}")
    String uploadFileDir;
    @Value("${python.file.dir}")
    String pythonFileDir;

    @Test
    public void readPython() {
        try {
            int imageInfoIndex = 0;
            //파이썬 실행 경로
            String path = "python3" + "\t" + pythonFileDir + "ocr.py" + "\t" + uploadFileDir + "ocrtest.jpg";
            System.out.println("path:" + path);
            String s = null;
            Process p = Runtime.getRuntime().exec(path);
            BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
            while ((s = in.readLine()) != null) {
                ++imageInfoIndex;
                System.out.println(s);
            }
        } catch (IOException ie) {
            ie.printStackTrace();
        }
    }

    @Test
    public void ocrParsingTest() {
//        String a = "001 필라이트캔50014Ｌ         1     1,190원";
//        String[] par = makeGoodsInfo(a);
//        System.out.println("test:" + par[0]);
//        System.out.println("test:" + par[1]);
//        System.out.println("test:" + par[2]);


        Map<String, String> rslt = new HashMap<>();
        Map<Integer, String[]> goods = new HashMap<>();
        //첫 두 개의 문자를 가져와서 필요한 데이터인지 판단한다.
        String s = "001 필라이트캔50014Ｌ         1     1,190원";
        int goodNum = 0;
        String pars = "";
        pars = s.substring(0, 2);
        System.out.println(pars);

        if (s.length() < 2) {
            System.out.println("testtest222");
        } else {
            pars = s.substring(0, 2);
            if (pars.equals("수퍼")) { //지점 이름,지점 위치
                rslt.put("branch", s);
////                in.readLine();
////                in.readLine();
//                String where = in.readLine();
//                rslt.put("where", where);
            } else if (pars.equals("00")) { //상품
                //  goods.put() //상품의 이름, 수량 , 가격
                System.out.println("testtest");
                String[] words = makeGoodsInfo(s);
                goods.put(goodNum, words);
                goodNum++;
            } else if (pars.equals("구매")) { // 상품 금액
                String tmp = s.replace(" ", "");  //공백제거
                String[] strings = tmp.split("액"); //'액' 문자 기준으로 문자열 나누기
                rslt.put("totalPrice", strings[1]); // 문자결 파싱 결과 저장
            }
        }

        rslt.put("goods", goods.toString());
        System.out.println(rslt.toString());
    }

    @Test
    public String[] makeGoodsInfo(String s) {
        String name = "";
        String num = "";
        String price = "";
        int numFindStart = 0;
        int priceFindStart = 0;

        for (int i = 4; i < s.length(); i++) {
            if (s.charAt(i) == ' ') {
                name = s.substring(4, i);
                numFindStart = i;
                break;
            }
        }
        for (int i = numFindStart; i < s.length(); i++) {
            if (s.charAt(i) != ' ') {
                num = String.valueOf(s.charAt(i));
                priceFindStart = i + 1;
                break;
            }
        }
        for (int i = priceFindStart; i < s.length(); i++) {
            if (s.charAt(i) != ' ') {
                price = s.substring(i, s.length() - 1);
                break;
            }
        }

        String[] goodsInfo = {name, num, price};
        return goodsInfo;
    }

    @Test
    public void parse2() {
        String s = "구매금액                 10,890원";
        String a = s.replace(" ", "");
        String[] b = a.split("액");
        System.out.println(b[1]);
    }


}