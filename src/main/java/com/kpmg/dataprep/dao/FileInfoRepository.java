package com.kpmg.dataprep.dao;

import com.kpmg.dataprep.domain.FileInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileInfoRepository extends JpaRepository<FileInfo, Long> {
}
