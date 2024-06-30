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
    public String getWorklistPage(HttpSession session){
        if(session.getAttribute("user") != null){
        return "worklist/worklist";
        }else{
            return "redirect:/user/login";
        }
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
            @RequestParam(required = false) String pname,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate)
    {

        if (pid != null && !pid.isEmpty() && pname != null && !pname.isEmpty()) {
            return studyService.findByPidPnameAndDateRange(pid, pname, startDate, endDate, PageRequest.of(page, size));
        } else if (pid != null && !pid.isEmpty()) {
            return studyService.findByPidContainingAndDateRange(pid, startDate, endDate, PageRequest.of(page, size));
        } else if (pname != null && !pname.isEmpty()) {
            return studyService.findByPnameContainingAndDateRange(pname, startDate, endDate, PageRequest.of(page, size));
        } else if (startDate != null && endDate != null) {
            return studyService.findByDateRange(startDate, endDate, PageRequest.of(page, size));
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