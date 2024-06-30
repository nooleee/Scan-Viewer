package com.pacs.scanviewer.pacs.Worklist.controller;

import com.pacs.scanviewer.pacs.Study.domain.Study;
import com.pacs.scanviewer.pacs.Worklist.service.WorklistService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@RequiredArgsConstructor
@Controller
public class WorklistController {
    // 서비스에서 페이징된 데이터를 모델에 추가, ModelAndView 반환

    private final WorklistService worklistService;

    @GetMapping("/studies")
    public ModelAndView viewStudies(@RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "10") int size,
                                    Model model) {
        Page<Study> studyPage = worklistService.findPaginated(page, size);
        ModelAndView modelAndView = new ModelAndView("studies");
        modelAndView.addObject("studyPage", studyPage);
        return modelAndView;
    }

}
