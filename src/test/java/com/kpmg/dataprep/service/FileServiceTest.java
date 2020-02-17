package com.kpmg.dataprep.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
@SpringBootTest
public class FileServiceTest {
    @Value("${file.upload.dir}")
    String uploadFileDir;

    @Test
    public void uploadFileDir() {
        System.out.println(uploadFileDir);
    }


}