package com.pacs.scanviewer.SCV.controller;

import com.pacs.scanviewer.SCV.domain.Report;
import com.pacs.scanviewer.SCV.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/report")
public class ReportController {
    private final ReportService reportService;

    @PostMapping
    public ResponseEntity<Report> createReport(@RequestBody Report report) {
        Report savedReport = reportService.save(report);
        return new ResponseEntity<>(savedReport, HttpStatus.CREATED);
    }
}
