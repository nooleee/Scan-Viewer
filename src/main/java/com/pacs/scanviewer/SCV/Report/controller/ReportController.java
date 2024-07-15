package com.pacs.scanviewer.SCV.Report.controller;

import com.pacs.scanviewer.SCV.Report.domain.Report;
import com.pacs.scanviewer.SCV.Report.service.ReportService;
import com.pacs.scanviewer.pacs.Study.domain.Study;
import com.pacs.scanviewer.pacs.Study.service.StudyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/report")
public class ReportController {
    private final ReportService reportService;
    private final StudyService studyService;

    @GetMapping("/ByStudyKey")
    @ResponseBody
    public ResponseEntity<Report> getReportByStudyKey(@RequestParam("studyKey") int studyKey) {
        Report report = reportService.getReportByStudyKey(studyKey);
        if (report != null) {
            return ResponseEntity.ok(report);
        } else {
            return ResponseEntity.ok(new Report());
        }
    }

    @GetMapping("/check/{studyKey}")
    public ResponseEntity<Map<String, Boolean>> checkReportExists(@PathVariable int studyKey) {
        boolean exists = reportService.checkIfStudyKeyExists(studyKey);
        Map<String, Boolean> response = new HashMap<>();
        response.put("exists", exists);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/view/{studyKey}")
    public ModelAndView viewReport(@PathVariable int studyKey, Model model) {
        boolean exists = reportService.checkIfStudyKeyExists(studyKey);
        List<Study> study = studyService.findByStudykey(studyKey);
        Report report = reportService.getReportByStudyKey(studyKey);

        model.addAttribute("study", study);
        if (exists) {
            model.addAttribute("report", report);
            return new ModelAndView("report/reportUpdate", model.asMap());
        } else {
            return new ModelAndView("report/report", model.asMap());
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
