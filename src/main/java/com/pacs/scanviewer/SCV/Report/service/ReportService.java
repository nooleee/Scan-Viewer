package com.pacs.scanviewer.SCV.Report.service;

import com.pacs.scanviewer.SCV.Report.domain.Report;
import com.pacs.scanviewer.SCV.Report.domain.ReportId;
import com.pacs.scanviewer.SCV.Report.domain.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ReportService {
    private final ReportRepository reportRepository;

    public Report save(Report report) {
        return reportRepository.save(report);
    }

    public Report update(Report report) {
        return reportRepository.save(report);
    }

    public void delete(int studyKey, String userCode) {
        ReportId reportId = new ReportId();
        reportId.setStudyKey(studyKey);
        reportId.setUserCode(userCode);
        reportRepository.deleteById(reportId);
    }

    public Report getReportByStudyKey(int studyKey) {
        return reportRepository.findByStudyKey(studyKey);
    }
}
