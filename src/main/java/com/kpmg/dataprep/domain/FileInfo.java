package com.kpmg.dataprep.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.Date;

@Entity(name = "fileInfo")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class FileInfo {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY) //기본 키 생성을 데이터베이스에 위임한다. Mysql-Autoincrement
    Long id;

    String FileNm; //인덱스
    String storedPath;
    String createId;
    LocalDateTime createDt;
}
