package com.pacs.scanviewer.SCV.ReportLog.service;

import com.pacs.scanviewer.SCV.ReportLog.domain.ReportLog;
import com.pacs.scanviewer.SCV.ReportLog.domain.ReportLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ReportLogService {
    private final ReportLogRepository reportLogRepository;

    public ReportLog createLog(ReportLog reportLog) { return reportLogRepository.save(reportLog); }

    public ReportLog updateLog(ReportLog reportLog) { return reportLogRepository.save(reportLog); }
}
