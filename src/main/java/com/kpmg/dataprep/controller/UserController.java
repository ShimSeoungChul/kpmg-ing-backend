package com.kpmg.dataprep.controller;

import com.kpmg.dataprep.service.UserService;
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

    //사용자의 현재 포인트를 가져온다.
    @GetMapping(value = "/{id}")
    public int getUser(@PathVariable String id){
        System.out.println(id);
        int point = userService.getUser(id);

        return point;
    }

    //사용자의 각 작업(3가지 작업)에 대한 포인지트를 가져온다.
    @GetMapping(value = "/eachPoint/{id}")
    public Map<String, Integer> getUserEachPoint(){
        Map<String, Integer> rslt = new HashMap<>();
        rslt.put("image",30);
        rslt.put("ocr",30);
        rslt.put("stt",40);
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
    @GetMapping(value = "/works/{id}")
    public void getUserWorks(){

    }

}
