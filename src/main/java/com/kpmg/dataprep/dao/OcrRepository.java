package com.kpmg.dataprep.dao;

import com.kpmg.dataprep.domain.Ocr;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;


public interface OcrRepository extends JpaRepository<Ocr, Long> {
    Optional<Ocr> findByFileId(String fileId);
}
