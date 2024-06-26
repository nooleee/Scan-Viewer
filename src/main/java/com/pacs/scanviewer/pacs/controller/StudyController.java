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
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Controller
public class StudyController {

    private final StudyService studyService;

    @ResponseBody
    @GetMapping("/studies")
    public ResponseEntity<List<Study>> getAllStudies() {
        List<Study> result = studyService.findAll();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

//    @ResponseBody
//    @GetMapping("/studies/{page}")
//    public ResponseEntity<List<Study>> getAllStudies(@PathVariable int page,@PageableDefault(size = 5) Pageable pageable) {
//        List<Study> result = studyService.findAllWithPage(page - 1, pageable);
//        return new ResponseEntity<>(result, HttpStatus.OK);
//    }

    @ResponseBody
    @GetMapping("/studies/{studykey}")
    public ResponseEntity<Map<Long, List<String>>> getStudy(@PathVariable long studykey) {
        Map<Long, List<String>> imageNamesByStudyKey = studyService.getImageNamesByStudyKey(studykey);

        ResponseEntity<Map<Long, List<String>>> res = new ResponseEntity(imageNamesByStudyKey,HttpStatus.OK);

        return res;
    }
}
