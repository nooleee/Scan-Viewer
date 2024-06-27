package com.pacs.scanviewer.pacs.controller;

import com.pacs.scanviewer.pacs.domain.Study;
import com.pacs.scanviewer.pacs.service.StudyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Controller
public class StudyController {

    private final StudyService studyService;

    @GetMapping("/worklist")
    public String getWorklistPage(){
        return "worklist/worklist";
    }

    @CrossOrigin
    @GetMapping("/worklistAllSearch")
    @ResponseBody
    public Page<Study> getAllStudies(@RequestParam(defaultValue="0") int page, @RequestParam(defaultValue = "5") int size ) {
        return studyService.findStudiesWithPage(page, size);
    }

    @GetMapping("/worklistPrevious/{pid}")
    @ResponseBody
    public List<Study> getStudiesByPid(@PathVariable String pid) {
        return studyService.getStudiesByPid(pid);
    }

    @CrossOrigin
    @GetMapping("/searchStudies")
    @ResponseBody
    public Page<Study> searchStudies(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(required = false) String pid,
            @RequestParam(required = false) String pname) {

        if (pid != null && !pid.isEmpty() && pname != null && !pname.isEmpty()) {
            return studyService.findByPidContainingAndPnameContaining(pid, pname, PageRequest.of(page, size));
        } else if (pid != null && !pid.isEmpty()) {
            return studyService.findByPidContaining(pid, PageRequest.of(page, size));
        } else if (pname != null && !pname.isEmpty()) {
            return studyService.findByPnameContaining(pname, PageRequest.of(page, size));
        } else {
            return studyService.findStudiesWithPage(page, size);
        }
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

//    @ResponseBody
//    @GetMapping("/studies/{studykey}")
//    public ResponseEntity<Study> getStudy(@PathVariable String studykey) {
//
//    }
}
