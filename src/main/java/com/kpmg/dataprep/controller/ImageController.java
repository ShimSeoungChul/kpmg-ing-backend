package com.kpmg.dataprep.controller;

import com.kpmg.dataprep.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
public class ImageController {

    @Autowired
    ImageService imageService;

    @GetMapping(value = "/helloWorld")
    public String helloWorld(){
        return "HelloWorld";
    }

    //클라이언트에서 받은 이미지를 머신러닝(파이썬 파일)을 통해 이미지 정보 데이터를 생성하고, 생성한 데이터를 클라이언트에 전달
    @PostMapping(value = "/image/getImageInfo")
    public Map<String , String> geImageInfo(MultipartHttpServletRequest multiRequest, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, String> map = new HashMap<>();
        map = imageService.makeImageInfo(multiRequest);

        return map;
    }

}
