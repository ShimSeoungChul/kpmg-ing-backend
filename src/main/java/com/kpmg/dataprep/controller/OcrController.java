package com.kpmg.dataprep.controller;


import com.kpmg.dataprep.service.ImageService;
import com.kpmg.dataprep.service.OcrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/ocr")
public class OcrController {
    String tag = "OcrController";

    @Autowired
    OcrService ocrService;

    //클라이언트에서 받은 이미지를 머신러닝(파이썬 파일)을 통해 ocr 정보 데이터를 생성하고, 생성한 데이터를 클라이언트에 전달
    @PostMapping(value = "/getOcrInfo")
    public Map<String , Object> getOcrInfo(HttpServletRequest request) throws Exception {
        System.out.println(tag+"getOcrInfo");
        Map<String, Object> map = ocrService.makeOcrInfo(request);

        return map;
    }

    @PutMapping(value = "/")
    public Map<String , String> update(HttpServletRequest request){
        System.out.println(tag+"updateImageInfo");
        Map<String, String> map = ocrService.update(request);

        return map;
    }




}
