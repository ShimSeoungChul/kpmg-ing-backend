package com.kpmg.dataprep.service;

import com.kpmg.dataprep.cmmn.CmmnUtil;
import com.kpmg.dataprep.dao.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import  com.kpmg.dataprep.domain.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

/*
이미지 로직
안드로이드:전처리할 이미지를 서버에 전송
-> 서버:1.이미지 받기 2.이미지 정보 디렉토리와 DB에 저장 3.파이썬 파일을 통해 이미지 정보 분석
4.분석된 이미지 정보과 이미지 아이디를 return
-> 안드로이드: 1.이미지 정보를 화면에 있는 3개의 버튼에 뿌리기  2.이미지 아이디를 list 형태로 저장
3.버튼 누르면 전처리 정보 list로 저장후 다음 화면으로 넘어감 4.완료 버튼을 누르면 지금까지 저장한 데이터를 서버에 저장
-> 서버: 이미지 전처리 정보를 데이터베이스에 저장
 */

@Service
public class ImageService {
    String tag = "ImageService";

    @Autowired
    FileService fileService;
    @Autowired
    ImageRepository imageRepository;

    @Value("${file.upload.dir}")
    String uploadFileDir;
    @Value("${python.file.dir}")
    String pythonFileDir;

//    public Image getImage(){
//    }

//    public List<Image> getImages(){
//        List<Image> images = imageRepository.findAll();
//        return images;
//    }

    //파이썬 파일의 머신러닝 코드를 통해 이미지 정보 데이터 생성하기
    public Map<String,String> makeImageInfo(HttpServletRequest request){
        System.out.println(tag+"makeImageInfo");

   Map<String, String> rslt = new HashMap<>();
   //이미지를 파일로 저장
    String imageNm = fileService.addFile(request);
        rslt.put("imageNm",imageNm);

    //이미지 정보를 데이터베이스에 저장
    Image saveImage = Image.builder()
            .fileId(imageNm)
            .workerName("test")
            .build();
    Image saved = insert(saveImage);

    //파이썬 파일을 통해 이미지 정보 가져와서 변수에 저장하기
        try{
            int imageInfoIndex = 0;
            //파이썬 실행 경로
            String path = "python3"+"\t"+pythonFileDir+"object_classification.py"+"\t"+uploadFileDir+imageNm;
            System.out.println("path:"+path);
            String s = null;
            Process p = Runtime.getRuntime().exec(path);
            BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
            while((s = in.readLine()) != null){
                rslt.put(Integer.toString(imageInfoIndex),s);
                ++imageInfoIndex;
                System.out.println(s);
            }
            rslt.put("rslt","SUCC");
        }catch (IOException ie){
            rslt.put("rslt","FAIL");
            System.out.println(tag+"실패원인:"+ie.getMessage()+"\n");
            return rslt;
        }

    //이미지 정보 저장
    return rslt;
    }

    public Image insert(Image image){
        Image saved = imageRepository.save(image);
        return saved;
    }

    public Map<String, String> update(HttpServletRequest request){
        Map<String, String> rslt = new HashMap<>();
        try {
            String jsnStr = CmmnUtil.getJsnStr(request);
            String fileId = CmmnUtil.getStrValFromJsnStr(jsnStr, "fileId");
            String imageName = CmmnUtil.getStrValFromJsnStr(jsnStr, "imageName");

            Image image = Image.builder()
                    .fileId(fileId)
                    .imageName(imageName)
                    .build();
            Optional<Image> findImage = imageRepository.findByFileId(image.getFileId());

            findImage.map(selectImage ->{
                selectImage.setImageName(image.getImageName());
                selectImage.setUpdateTime(LocalDateTime.now());
                Image updated = imageRepository.save(selectImage);
                if(updated != null){
                    rslt.put("rslt","SUCC");
                }else {
                    System.out.println(tag+"실패원인:update 실패 \n");
                    rslt.put("rslt","FAIL");
                }
                return rslt;
            });

            return rslt;
        }catch (Exception e){
            System.out.println(tag+"실패원인:"+e.getMessage()+"\n");
            rslt.put("rslt","FAIL");
            return rslt;
        }
    }

    public Image select(Long id){
        Image selected = imageRepository.getOne(id);
        return selected;
    }


//    public List<Image> updateList(HttpServletRequest request){
//        List<Image> imageList = new ArrayList<Image>;
//
//        return imageList;
//    }

}
