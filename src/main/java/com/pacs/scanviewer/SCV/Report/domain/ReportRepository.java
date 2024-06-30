package com.pacs.scanviewer.SCV.Report.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository extends JpaRepository<Report, Integer> {

    Report findByStudyKey(int studyKey);
}