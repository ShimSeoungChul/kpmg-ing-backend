package com.kpmg.dataprep.dao;

import  com.kpmg.dataprep.domain.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image, Long> {
    Optional<Image> findByFileId(String fileId);
    ArrayList<Image> findByWorkerName(String workerName);
}
