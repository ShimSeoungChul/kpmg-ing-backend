package com.kpmg.dataprep.service;

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
import java.util.HashMap;
import java.util.Map;

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
    public Map<String,String> makeOcrInfo(HttpServletRequest request){
      Map<String, String> rslt = new HashMap<>();
        //ocr 처리용 이미지를 파일로 저장
        String ocrImageNm = fileService.addFile(request);
        rslt.put("ocrImageNm",ocrImageNm);

        //ocr 처리용 이미지를 정보를 데이터베이스에 저장
        Ocr saveOcrImage = Ocr.builder()
                .fileId(ocrImageNm)
                .workerName("test")
                .build();
        Ocr saved = insert(saveOcrImage);

        try{
            int imageInfoIndex = 0;
            //파이썬 실행 경로
            String path = "python3"+"\t"+pythonFileDir+"ocr.py"+"\t"+uploadFileDir+ocrImageNm;
            System.out.println("path:"+path);
            String s = null;
            Process p = Runtime.getRuntime().exec(path);
            BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
            int i = 0;
            Map<String,String> goods = new HashMap<>();
            while((s = in.readLine()) != null){
                //첫 두 개의 문자를 가져와서 필요한 데이터인지 판단한다.
                String pars = s.substring(0,2);

                if(pars.equals("수퍼")){ //지점 이름,지점 위치
                    rslt.put("branch",s);
                    in.readLine();
                    in.readLine();
                    String where = in.readLine();
                    rslt.put("where",where);
                }else if(pars.equals("00")){ //상품
                  //  goods.put() //상품의 이름, 수량 , 가격
                    String [] words = s.split("\\s");
                }else if(pars.equals("내신")){ // 상품 금액

                }
                System.out.println(s);
            }
            rslt.put("rslt","SUCC");

          return rslt;
      }catch (Exception e){
          rslt.put("rslt","FAIL");
          System.out.println(tag+"실패원인:"+e.getMessage()+"\n");
          return rslt;
      }
    }

    //한 문장에 들어있는 물품, 수량, 금액 정보를 가져오는 메소드
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

    public Ocr insert(Ocr ocr){
        Ocr saved = ocrRepository.save(ocr);
        return saved;
    }

}
