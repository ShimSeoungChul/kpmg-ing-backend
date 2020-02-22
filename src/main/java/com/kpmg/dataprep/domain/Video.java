package com.kpmg.dataprep.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;


//30point
@Entity(name="video")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Video {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY) //기본 키 생성을 데이터베이스에 위임한다. Mysql-Autoincrement
    private Long id;

    private String videoFileId; //영상 파일 이름
    private String speechFileId; //음성 파일 이름
    private String workerName; //작업자 이름
    private String type; //lidar or stt or original
    private LocalDateTime updateTime; //작업한 시간

    @Lob
    @Column(length=2024)
    private String videoInfo; //전처리 작업 후 이미지 파일의 video 내용


}
