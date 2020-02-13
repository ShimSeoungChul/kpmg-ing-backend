package com.kpmg.dataprep.service;

import com.kpmg.dataprep.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import  com.kpmg.dataprep.domain.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;


@Service
public class ImageService {
    @Autowired
    FileService fileService;
    @Autowired
    ImageRepository imageRepository;

    @Value("${file.upload.dir}")
    String uploadFileDir;
    @Value("${python.file.dir}")
    String pythonFileDir;


//    @Autowired
//    FileRepository fileRepository;

    public Image addImage(Image image){
        Image saved = imageRepository.save(image);
        return saved;
    }

//    public Image updateImage(Image image, Long id){
//        Optional<Image> findImage = imageRepository.findById(id);
//        Image updated = new Image();
//
//        findImage.ifPresent(selectImage ->{
//            selectImage.setUserAccount("modUser1");
//            selectImage.setEmail("modUser1@gmail.com");
//            selectImage.setModDt(LocalDateTime.now());
//            selectImage.setModUser("shlee0882");
//            updated = imageRepository.save(selectImage);
//            System.out.println("imageUpate: "+updated);
//        });
//
//        return updated;
//    }

//    public Image getImage(){
//    }

//    public List<Image> getImages(){
//        List<Image> images = imageRepository.findAll();
//        return images;
//    }

    //파이썬 파일의 머신러닝 코드를 통해 이미지 정보 데이터 생성하기
    public Map<String,String> makeImageInfo(MultipartHttpServletRequest multiRequest) throws Exception {
    //이미지 저장
    String filename = fileService.addFile(multiRequest);

    Map<String, String> map = new HashMap<>();
    //파이썬 파일을 통해 이미지 정보 가져와서 변수에 저장하기
        try{
            int imageInfoIndex = 0;
            //파이썬 실행 경로
            String path = "python3"+"\t"+pythonFileDir+"object_classification.py"+"\t"+uploadFileDir+filename;
            System.out.println("path:"+path);
            String s = null;
            Process p = Runtime.getRuntime().exec(path);
            BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
            while((s = in.readLine()) != null){
                map.put(Integer.toString(imageInfoIndex),s);
                ++imageInfoIndex;
                System.out.println(s);
            }
        }catch (IOException ie){
            ie.printStackTrace();
        }

    //이미지 정보 저장

    return map;
    }

}
