package com.pacs.scanviewer.pacs.Worklist.controller;

import com.pacs.scanviewer.SCV.Consent.service.ConsentService;
import com.pacs.scanviewer.Util.CookieUtil;
import com.pacs.scanviewer.Util.JwtUtil;
import com.pacs.scanviewer.pacs.Search.domain.SearchResponseDTO;
import com.pacs.scanviewer.pacs.Search.service.SearchService;
import com.pacs.scanviewer.pacs.Study.domain.Study;
import com.pacs.scanviewer.pacs.Study.service.StudyService;
import com.pacs.scanviewer.pacs.Worklist.service.WorklistService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class WorklistController {
    // 서비스에서 페이징된 데이터를 모델에 추가, ModelAndView 반환

    private final WorklistService worklistService;
    private final StudyService studyService;
    private final JwtUtil jwtUtil;
    private final SearchService searchService;

    @GetMapping("/studies")
    public ModelAndView viewStudies(@RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "10") int size,
                                    Model model) {
        Page<Study> studyPage = worklistService.findPaginated(page, size);
        ModelAndView modelAndView = new ModelAndView("studies");
        modelAndView.addObject("studyPage", studyPage);
        return modelAndView;
    }

    @GetMapping("/worklist")
    public String getWorklistPage() {
        return "worklist/worklist";
    }

    @CrossOrigin
    @GetMapping("/studiesByPid/{pid}")
    @ResponseBody
    public List<SearchResponseDTO> findStudiesByPid(@PathVariable String pid, HttpServletRequest request) {
        String token = CookieUtil.getCookieValue(request, "jwt");
        String userCode = jwtUtil.extractUsername(token);

        return searchService.findStudiesByPid(pid, userCode);
    }
}
