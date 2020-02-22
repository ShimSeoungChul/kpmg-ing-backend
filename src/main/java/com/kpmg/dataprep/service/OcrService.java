package com.kpmg.dataprep.service;

import com.kpmg.dataprep.cmmn.CmmnUtil;
import com.kpmg.dataprep.dao.OcrRepository;
import com.kpmg.dataprep.domain.Image;
import com.kpmg.dataprep.domain.Ocr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class OcrService {
    String tag = "OcrService";

    @Autowired
    FileService fileService;
    @Autowired
    OcrRepository ocrRepository;

    @Value("${file.upload.dir}")
    String uploadFileDir;
    @Value("${python.file.dir}")
    String pythonFileDir;

    //파이썬 파일의 머신러닝 코드를 통해 OCR 정보 데이터 생성하기
    public Map<String, Object> makeOcrInfo(HttpServletRequest request) {
        Map<String, Object> rslt = new HashMap<>();
        //ocr 처리용 이미지를 파일로 저장
        String ocrImageNm = fileService.addFile(request);
        rslt.put("ocrImageNm", ocrImageNm);

        try {
            int imageInfoIndex = 0;
            //파이썬 실행 경로
            String path = "python3" + "\t" + pythonFileDir + "ocr.py" + "\t" + uploadFileDir + ocrImageNm;
            System.out.println("path:" + path);
            String s = null;
            Process p = Runtime.getRuntime().exec(path);
            System.out.println("tes1\n");
            BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
            System.out.println("tes2\n");
            int goodNum = 0;
            Map<Integer, Object> goods = new HashMap<>();
            while ((s = in.readLine()) != null) {
                //첫 두 개의 문자를 가져와서 필요한 데이터인지 판단한다.
                String pars = "";
                if (s.length() < 2) {

                } else {
                    pars = s.substring(0, 2);
                    if (pars.equals("수퍼")) { //지점 이름,지점 위치
                        rslt.put("branch", s);
                        in.readLine();
                        in.readLine();
                        String where = in.readLine();
                        rslt.put("where", where);
                    } else if (pars.equals("00")) { //상품
                        //  goods.put() //상품의 이름, 수량 , 가격
                        String[] words = makeGoodsInfo(s);
                        rslt.put("name",words[0]);
                        rslt.put("num",words[1]);
                        rslt.put("price",words[2]);
                    } else if (pars.equals("구매")) { // 상품 금액
                        String tmp = s.replace(" ", "");  //공백제거
                        String[] strings = tmp.split("액"); //'액' 문자 기준으로 문자열 나누기
                        rslt.put("totalPrice", strings[1]); // 문자결 파싱 결과 저장
                    }
                }
            }

            rslt.put("goods", goods);
            rslt.put("rslt", "SUCC");
            System.out.println(rslt.toString());

            return rslt;
        } catch (Exception e) {
            rslt.put("rslt", "FAIL");
            System.out.println(tag + "실패원인:" + e.getMessage() + "\n");
            return rslt;
        }
    }

    //한 문장에 들어있는 물품, 수량, 금액 정보를 가져오는 메소드
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

    public Ocr insert(Ocr ocr) {
        Ocr saved = ocrRepository.save(ocr);
        return saved;
    }

    public int getPoint(String id){
        ArrayList<Ocr> selected = ocrRepository.findByWorkerName(id);
        System.out.println(selected.size());
        int point = 0;
        for (Ocr tmp : selected) {
            if(tmp.getUpdateTime() != null && !tmp.getUpdateTime().equals("") ){
                point = point+20;
            }
        }
        return point;
    }

}
