package com.kpmg.dataprep.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;


@Entity(name = "image")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Image {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY) //기본 키 생성을 데이터베이스에 위임한다. Mysql-Autoincrement
    private Long id;

    private String fileId;  //이미지 파일 이름
    private String imageName; //전처리 작업 후 이미지 이름
    private String workerName; //작업자 이름
    private LocalDateTime updateTime; //작업한 시간
}


