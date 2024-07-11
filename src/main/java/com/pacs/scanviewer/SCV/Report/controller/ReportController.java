package com.pacs.scanviewer.SCV.Report.controller;

import com.pacs.scanviewer.SCV.Report.domain.Report;
import com.pacs.scanviewer.SCV.Report.service.ReportService;
import com.pacs.scanviewer.pacs.Study.service.StudyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/report")
public class ReportController {
    private final ReportService reportService;

    @GetMapping("/ByStudyKey")
    @ResponseBody
    public ResponseEntity<Report> getReportByStudyKey(@RequestParam("studyKey") int studyKey) {
        System.out.println(studyKey);
        Report report = reportService.getReportByStudyKey(studyKey);
        if (report != null) {
            return ResponseEntity.ok(report);
        } else {
            return ResponseEntity.ok(new Report());
        }
    }

    @PostMapping("/{studyKey}")
    public ResponseEntity<?> createReport(@ModelAttribute Report report) {
        reportService.save(report);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{studyKey}")
    public ResponseEntity<?> updateReport(@ModelAttribute Report report) {
        reportService.update(report);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/searchICD")
    public ResponseEntity<String> searchICDCode(@RequestParam("query") String query) {
        try {
            String result = reportService.searchICDCode(query);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }
}
