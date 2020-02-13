package com.kpmg.dataprep.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;


@Entity(name = "image")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Image {

    @Id
    @GeneratedValue
    Long id;

    Long fileId;  //이미지 파일 번호
    String imageName; //전처리 작업 후 이미지 이름

    //index
    String workerName; //작업자 이름, 인덱스

    //index
    Date updateTime; //작업한 시간, 인덱스
}


