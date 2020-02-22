package com.kpmg.dataprep.dao;

import com.kpmg.dataprep.domain.Video;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface VideoRepository extends JpaRepository<Video, Long> {
    ArrayList<Video> findByWorkerName(String workerName);
}
