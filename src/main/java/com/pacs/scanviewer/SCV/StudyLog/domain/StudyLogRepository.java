package com.pacs.scanviewer.SCV.StudyLog.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;

@Repository
public interface StudyLogRepository extends JpaRepository<StudyLog, Integer> {
    public void deleteByRegDateBefore(Timestamp timestamp);

}
