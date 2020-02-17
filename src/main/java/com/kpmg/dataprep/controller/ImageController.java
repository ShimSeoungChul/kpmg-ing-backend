package com.kpmg.dataprep.controller;

import com.kpmg.dataprep.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/images")
public class ImageController {
    String tag = "ImageController";

    @Autowired
    ImageService imageService;

    @GetMapping(value = "/helloWorld")
    public String helloWorld(){
        System.out.println("helloWorld");
        return "HelloWorld";
    }

    //클라이언트에서 받은 이미지를 머신러닝(파이썬 파일)을 통해 이미지 정보 데이터를 생성하고, 생성한 데이터를 클라이언트에 전달
    @PostMapping(value = "/getImageInfo")
    public Map<String , String> geImageInfo(HttpServletRequest request) throws Exception {
        System.out.println(tag+"geImageInfo");
        Map<String, String> map = imageService.makeImageInfo(request);

        return map;
    }

    @PutMapping(value = "/updateImageInfo")
    public Map<String , String> updateImageInfo(HttpServletRequest request){
        System.out.println(tag+"updateImageInfo");
        Map<String, String> map = imageService.update(request);

        return map;
    }

//    @PutMapping(value = "/images/")
//    public int updateList(HttpServletRequest request){
//        //이미지 정보 업데이트 및 업데이트 성공한 개수 Return
//        List<Image> imageList = new ArrayList<Image>;
//        imageList = imageService.updateList(request);
//
//        return imageList.size();
//    }

}
