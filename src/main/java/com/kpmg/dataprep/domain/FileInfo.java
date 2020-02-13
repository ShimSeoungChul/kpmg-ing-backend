package com.kpmg.dataprep.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity(name = "fileInfo")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FileInfo {
    @Id
    Long id;

    String FileNm; //인덱스
    String storedPath;
    String createId;
    Date createDt;
}
