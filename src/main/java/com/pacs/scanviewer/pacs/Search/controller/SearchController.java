package com.pacs.scanviewer.pacs.Search.controller;

import com.pacs.scanviewer.SCV.Consent.domain.Consent;
import com.pacs.scanviewer.SCV.Consent.service.ConsentService;
import com.pacs.scanviewer.SCV.Report.domain.Report;
import com.pacs.scanviewer.SCV.Report.service.ReportService;
import com.pacs.scanviewer.Util.CookieUtil;
import com.pacs.scanviewer.Util.JwtUtil;
import com.pacs.scanviewer.pacs.Search.domain.SearchRequestDTO;
import com.pacs.scanviewer.pacs.Search.domain.SearchResponseDTO;
import com.pacs.scanviewer.pacs.Search.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RequiredArgsConstructor
@Controller
@RequestMapping("/search")
public class SearchController {

    private final SearchService searchService;
    private final JwtUtil jwtUtil;
    private final ConsentService consentService;
    private final ReportService reportService;

    @CrossOrigin
    @GetMapping("/studies")
    @ResponseBody
    public Page<SearchResponseDTO> searchStudies(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            SearchRequestDTO searchDTO, HttpServletRequest request) {
        String token = CookieUtil.getCookieValue(request, "jwt");
        String userCode = jwtUtil.extractUsername(token);

        Page<SearchResponseDTO> searchResponseDTOS = searchService.searchStudies(searchDTO, PageRequest.of(page, size));

        searchResponseDTOS.forEach(dto -> {
            Optional<Consent> consent = consentService.findConsentByStudyKeyAndUserCode((int) dto.getStudykey(), userCode);
            dto.setExamstatus(consent.isPresent() ? 1 : 0);

            Optional<Report> reportOptional = reportService.findReportByStudyKey((int) dto.getStudykey());
            if (reportOptional.isPresent()) {
                Report report = reportOptional.get();
                if (report.getVideoReplay().equals(Report.VideoReplay.판독완료)) {
                    dto.setReportstatus(2);
                } else {
                    dto.setReportstatus(1);
                }
            } else {
                dto.setReportstatus(0);
            }
        });

        return searchResponseDTOS;
    }
}
