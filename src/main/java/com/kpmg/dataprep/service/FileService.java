package com.kpmg.dataprep.service;

import com.kpmg.dataprep.cmmn.CmmnUtil;
import com.kpmg.dataprep.domain.FileInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

@Service
public class FileService {

    @Value("${file.upload.dir}")
    String uploadFileDir;


//    @Autowired
//    FileRepository fileRepository;

//    public File addFile(File file){
//        File saved = fileRepository.save(file);
//        return saved;
//    }

    //파일을 디렉토리에 저장하고, 파일 이름을 데이터베이스에 입력한다.
    public String addFile(MultipartHttpServletRequest multiRequest) throws Exception {
        Map<String, MultipartFile> files = multiRequest.getFileMap();
        Iterator<Entry<String, MultipartFile>> iterator = files.entrySet().iterator();

        /** 첨부파일 위치 지정 */
        MultipartFile multipartFile = null;
        String originalFileName = null;
        String storedFileName = null;

        // 파일 업로드 경로
        String uploadDir = uploadFileDir;

        File file = new File(uploadDir);
        if (file.exists() == false) {
            file.mkdirs();
        }

        while (iterator.hasNext()) {

            Entry<String, MultipartFile> entry = iterator.next();
            multipartFile = entry.getValue();

            if (multipartFile.isEmpty() == false) {
                storedFileName = CmmnUtil.getRandomString();

                file = new File(uploadDir + storedFileName);
                // 지정된 경로에 파일을 생성
                multipartFile.transferTo(file);

//                returnMap = new EgovMap();
//                returnMap.put("storedFileName", storedFileName);
//                returnMap.put("originalFileName", originalFileName);
//                returnMap.put("fileSize", Long.toString(fileSize));
//                returnMap.put("storedPath", uploadDir);
//                returnList.add(returnMap);
            }
        }
        return storedFileName;
    }

}
