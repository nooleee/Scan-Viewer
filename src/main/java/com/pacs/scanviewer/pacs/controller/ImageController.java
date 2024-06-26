package com.pacs.scanviewer.pacs.controller;

import com.pacs.scanviewer.pacs.domain.Image;
import com.pacs.scanviewer.pacs.domain.Study;
import com.pacs.scanviewer.pacs.service.ImageService;
import com.pacs.scanviewer.pacs.service.StudyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.awt.print.Pageable;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class ImageController {

    private final ImageService imageService;
    private final StudyService studyService;

    @ResponseBody
    @GetMapping("/image")
    public ResponseEntity<List<Study>> getAllStudies() {
        List<Study> result = studyService.getAllStudies();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

//    @GetMapping("/list/page")
//    public ModelAndView getImageListWithPagination(@PageableDefault(size = 5) Pageable pageable) {
//        ModelAndView mv = new ModelAndView("image/list");
//        Page<Image> imagesPage = imageService.findAllWithPage((org.springframework.data.domain.Pageable) pageable);
//        mv.addObject("imagesPage", imagesPage);
//        return mv;
//    }

}
