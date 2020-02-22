package com.kpmg.dataprep.controller;

import com.kpmg.dataprep.service.ImageService;
import com.kpmg.dataprep.service.OcrService;
import com.kpmg.dataprep.service.UserService;
import com.kpmg.dataprep.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {
    String tag = "UserController";

    @Autowired
    UserService userService;

    @Autowired
    ImageService imageService;

    @Autowired
    OcrService ocrService;

    @Autowired
    VideoService videoService;


    //사용자의 현재 포인트를 가져온다.
    @GetMapping(value = "/{id}")
    public int getUser(@PathVariable String id){
        System.out.println(id);
        int point = userService.getUser(id);

        return point;
    }

    //사용자의 각 작업(3가지 작업)에 대한 포인지트를 가져온다.
    @GetMapping(value = "/eachPoint/{id}")
    public Map<String, Integer> getUserEachPoint(@PathVariable String id){
        System.out.println("id"+id);
        Map<String, Integer> rslt = new HashMap<>();
        int imagePoint = imageService.getPoint(id);
        int ocrPoint = ocrService.getPoint(id);
        int sttPoint = videoService.getPoint(id);
        rslt.put("image",imagePoint);
        rslt.put("ocr",ocrPoint);
        rslt.put("stt",sttPoint);
        return rslt;
    }

    //사용자의 랭킹을 가져온다.
    @GetMapping(value = "/rank/{id}")
    public Map<String, Integer> getUserRank(){
        Map<String, Integer> rslt = new HashMap<>();
        rslt.put("rank",2);
        rslt.put("percent",90);
        return rslt;
    }

    //사용자의 시간별 전체 작업을 가져온다.
    @GetMapping(value = "/work/{id}")
    public Map<String, Object> getUserWorks(){
        Map<String, Object>  rslt = new HashMap<>();

        Map<String, Object> work = new HashMap<>();
        work.put("time","2020-02-17 15:23:17");
        work.put("workType","이미지 분류");
        work.put("point","10");

        Map<String, Object> work2 = new HashMap<>();
        work2.put("time","2020-02-17 15:23:18");
        work2.put("workType","영수 텍스트 데이터 생성");
        work2.put("point","20");

        Map<String, Object> work3 = new HashMap<>();
        work3.put("time","2020-02-17 15:23:19");
        work3.put("workType","음성 자막 생성");
        work3.put("point","30");

        Map<String, Object> work4 = new HashMap<>();
        work4.put("time","2020-02-17 15:23:20");
        work4.put("workType","음성 자막 생성");
        work4.put("point","30");

        Map<String, Object> work5 = new HashMap<>();
        work5.put("time","2020-02-17 15:23:21");
        work5.put("workType","음성 자막 생성");
        work5.put("point","30");

        Map<String, Object> work6 = new HashMap<>();
        work6.put("time","2020-02-17 15:23:22");
        work6.put("workType","음성 자막 생성");
        work6.put("point","30");

        Map<String, Object> work7 = new HashMap<>();
        work7.put("time","2020-02-17 15:23:23");
        work7.put("workType","음성 자막 생성");
        work7.put("point","30");

        Map<String, Object> work8 = new HashMap<>();
        work8.put("time","2020-02-17 15:23:24");
        work8.put("workType","음성 자막 생성");
        work8.put("point","30");

        Map<String, Object> work9 = new HashMap<>();
        work9.put("time","2020-02-17 15:23:25");
        work9.put("workType","음성 자막 생성");
        work9.put("point","30");

        Map<String, Object> work10 = new HashMap<>();
        work10.put("time","2020-02-17 15:23:26");
        work10.put("workType","음성 자막 생성");
        work10.put("point","30");

        Map<String, Object> work11 = new HashMap<>();
        work11.put("time","2020-02-17 15:23:28");
        work11.put("workType","음성 자막 생성");
        work11.put("point","30");

        Map<String, Object> work12 = new HashMap<>();
        work12.put("time","2020-02-17 15:23:29");
        work12.put("workType","음성 자막 생성");
        work12.put("point","30");

        Map<String, Object> work13 = new HashMap<>();
        work13.put("time","2020-02-17 15:23:20");
        work13.put("workType","음성 자막 생성");
        work13.put("point","30");

        Map<String, Object> work14 = new HashMap<>();
        work14.put("time","2020-02-17 15:23:31");
        work14.put("workType","음성 자막 생성");
        work14.put("point","30");

        rslt.put("contentNum","14");
        rslt.put("0",work);
        rslt.put("1",work2);
        rslt.put("2",work3);
        rslt.put("3",work4);
        rslt.put("4",work5);
        rslt.put("5",work6);
        rslt.put("6",work7);
        rslt.put("7",work8);
        rslt.put("8",work9);
        rslt.put("9",work10);
        rslt.put("10",work11);
        rslt.put("11",work12);
        rslt.put("12",work13);
        rslt.put("13",work14);

        System.out.println(rslt);

        return rslt;
    }

}
