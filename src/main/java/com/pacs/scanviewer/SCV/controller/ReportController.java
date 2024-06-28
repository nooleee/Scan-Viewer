package com.pacs.scanviewer.SCV.controller;

import com.pacs.scanviewer.SCV.domain.Report;
import com.pacs.scanviewer.SCV.service.ReportService;
import com.pacs.scanviewer.pacs.domain.Study;
import com.pacs.scanviewer.pacs.service.StudyService;
import lombok.RequiredArgsConstructor;
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
    public ModelAndView getReportByStudyKey(@PathVariable long studyKey) {
        ModelAndView model = new ModelAndView("report/report");
        List<Study> reportList = studyService.findByStudykey(studyKey);
        System.out.println("reportList: " + reportList);
        model.addObject("reports", reportList);
        return model;
    }

    @PostMapping()
    public String createReport(@ModelAttribute Report report) {
        reportService.save(report);
        return "redirect:/worklist";
    }

    @PutMapping("/update")
    public String updateReport(@ModelAttribute Report report) {
        reportService.update(report);
        return "redirect:/report";
    }

    @DeleteMapping("/delete/{studyKey}")
    public String deleteReport(@PathVariable int studyKey) {
        reportService.delete(studyKey);
        return "redirect:/report";
    }
}
