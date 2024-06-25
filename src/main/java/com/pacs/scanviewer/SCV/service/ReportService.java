package com.pacs.scanviewer.SCV.service;

import com.pacs.scanviewer.SCV.domain.Report;
import com.pacs.scanviewer.SCV.domain.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ReportService {
    private final ReportRepository reportRepository;

    public List<Report> findAll() {
        return reportRepository.findAll();
    }

    public Report save(Report report) {
        return reportRepository.save(report);
    }

}
