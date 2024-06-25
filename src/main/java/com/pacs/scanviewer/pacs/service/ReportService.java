package com.pacs.scanviewer.pacs.service;

import com.pacs.scanviewer.pacs.domain.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ReportService {
    private final ReportRepository reportRepository;
}
