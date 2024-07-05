package com.pacs.scanviewer.SCV.Report.controller;

import com.pacs.scanviewer.SCV.Report.domain.Report;
import com.pacs.scanviewer.SCV.Report.service.ReportService;
import com.pacs.scanviewer.pacs.Study.domain.Study;
import com.pacs.scanviewer.pacs.Study.service.StudyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/report")
public class ReportController {
    private final ReportService reportService;
    private final StudyService studyService;

    @GetMapping("/{studyKey}")
    public ModelAndView reportByStudyKey(@PathVariable int studyKey) {
        ModelAndView model;
        Report report = reportService.getReportByStudyKey(studyKey);

        if (report == null) {
            model = new ModelAndView("report/report");
        } else {
            model = new ModelAndView("report/reportUpdate");
            model.addObject("report", report);
        }

        List<Study> reportList = studyService.findByStudykey(studyKey);
        model.addObject("reports", reportList);
        return model;
    }

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
    public void createReport(@ModelAttribute Report report) {
        reportService.save(report);
    }

    @PutMapping("/{studyKey}")
    public void updateReport(@ModelAttribute Report report) {
        if (report.getVideoReplay() == Report.VideoReplay.판독취소) {
            report.setContent("");
            report.setPatient("");
        }
        reportService.update(report);
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
