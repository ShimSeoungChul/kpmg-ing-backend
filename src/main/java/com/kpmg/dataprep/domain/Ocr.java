package com.kpmg.dataprep.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity(name = "ocr")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Ocr {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY) //기본 키 생성을 데이터베이스에 위임한다. Mysql-Autoincrement
    private Long id;

    private String fileId;  //이미지 파일 이름
    private String ocrInfo; //전처리 작업 후 이미지 파일의 ocr 내용
    private String workerName; //작업자 이름
    private LocalDateTime updateTime; //작업한 시간
}
