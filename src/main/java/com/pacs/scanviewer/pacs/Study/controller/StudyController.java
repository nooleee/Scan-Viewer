package com.pacs.scanviewer.pacs.Study.controller;

import com.pacs.scanviewer.pacs.Study.domain.Study;
import com.pacs.scanviewer.pacs.Study.service.StudyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class StudyController {

    private final StudyService studyService;

    @GetMapping("/worklist")
    public String getWorklistPage(HttpSession session) {
        if (session.getAttribute("user") != null) {
            return "worklist/worklist";
        } else {
            return "redirect:/user/login";
        }
    }

    @CrossOrigin
    @GetMapping("/worklistAllSearch")
    @ResponseBody
    public Page<Study> getAllStudies(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size) {
        return studyService.findStudiesWithPage(page, size);
    }

    @GetMapping("/worklistPrevious/{pid}")
    @ResponseBody
    public List<Study> getStudiesByPid(@PathVariable String pid) {
        return studyService.getStudiesByPid(pid);
    }

//    @ResponseBody
//    @GetMapping("/studies/{page}")
//    public ResponseEntity<List<Study>> getAllStudies(@PathVariable int page,@PageableDefault(size = 5) Pageable pageable) {
//        List<Study> result = studyService.findAllWithPage(page - 1, pageable);
//        return new ResponseEntity<>(result, HttpStatus.OK);
//    }

//    @ResponseBody
//    @GetMapping("/studies/{studykey}")
//    public ResponseEntity<Map<Long, List<String>>> getStudy(@PathVariable long studykey) {
//        Map<Long, List<String>> imageNamesByStudyKey = studyService.getImageNamesByStudyKey(studykey);
//
//        ResponseEntity<Map<Long, List<String>>> res = new ResponseEntity(imageNamesByStudyKey,HttpStatus.OK);
//
//        return res;
//    }

//    @ResponseBody
//    @GetMapping("/studies/{studykey}")
//    public ResponseEntity<Study> getStudy(@PathVariable String studykey) {
//
//    }
}
