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

    @GetMapping()
    public ModelAndView getAllReports() {
        ModelAndView model = new ModelAndView("report/report");
        List<Study> reportList = studyService.getAllStudies();
        model.addObject("reports", reportList);
        return model;
    }

    @PostMapping
    public String createReport(@ModelAttribute Report report) {
        reportService.save(report);
        return "redirect:/report";
    }

    @PutMapping("/update")
    public String updateReport(@ModelAttribute Report report) {
        reportService.update(report);
        return "redirect:/report";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteReport(@PathVariable int id) {
        reportService.delete(id);
        return "redirect:/report";
    }
}
