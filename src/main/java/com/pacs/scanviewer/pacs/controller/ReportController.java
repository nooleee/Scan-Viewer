package com.pacs.scanviewer.pacs.controller;

import com.pacs.scanviewer.pacs.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public class ReportController {
    private final ReportService reportService;
}
