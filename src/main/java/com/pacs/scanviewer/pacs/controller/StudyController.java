package com.pacs.scanviewer.pacs.controller;

import com.pacs.scanviewer.pacs.domain.Study;
import com.pacs.scanviewer.pacs.service.StudyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class StudyController {

    private final StudyService studyService;

    @GetMapping("/worklist")
    public ModelAndView getWorklist() {
        ModelAndView mv = new ModelAndView("worklist/worklist");
        List<Study> studies = studyService.getAllStudies();
        mv.addObject("studies", studies);
        return mv;
    }

    @ResponseBody
    @GetMapping("/studies/{page}")
    public ResponseEntity<List<Study>> getAllStudies(@PathVariable int page,@PageableDefault(size = 5) Pageable pageable) {
        List<Study> result = studyService.findAllWithPage(page - 1, pageable);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
