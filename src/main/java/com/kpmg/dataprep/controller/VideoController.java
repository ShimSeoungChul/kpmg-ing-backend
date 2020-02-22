package com.kpmg.dataprep.controller;


import com.kpmg.dataprep.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.Map;

@RestController
@RequestMapping("/videos")
public class VideoController {

    @Autowired
    VideoService videoService;

    @GetMapping(value = "/toText/{id}")
    public String getVideoToText(@PathVariable String id) throws IOException {
        String mp3FileName = videoService.videoToMp3(id);
        String sttText = videoService.mp3ToText(mp3FileName);
        return sttText;
    }
    @PostMapping(value = "/stt/")
    public Map<String , String> insertSttInfo(HttpServletRequest request){
        Map<String, String> map = videoService.insert(request);
        return map;
    }



}
