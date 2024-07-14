package com.pacs.scanviewer.SCV.Report.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReportRepository extends JpaRepository<Report, ReportId> {

    Report findByStudyKey(int studyKey);
    Optional<Report> findReportByStudyKey(int studykey);
    boolean existsByStudyKey(int studyKey);
}