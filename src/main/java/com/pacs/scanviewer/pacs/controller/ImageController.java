package com.pacs.scanviewer.pacs.controller;

import com.pacs.scanviewer.pacs.domain.Image;
import com.pacs.scanviewer.pacs.domain.Study;
import com.pacs.scanviewer.pacs.service.ImageService;
import com.pacs.scanviewer.pacs.service.StudyService;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.awt.print.Pageable;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequestMapping("/images")
@RequiredArgsConstructor
@Controller
public class ImageController {

    private final ImageService imageService;
    private final StudyService studyService;

    @GetMapping("/image")
    public ResponseEntity<List<Study>> getAllStudies() {
        List<Study> result = studyService.getAllStudies();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @GetMapping("/{studykey}/{serieskey}/{filename:.+}")
    @ResponseBody
    public ResponseEntity<FileSystemResource> getImage(@PathVariable Long studykey,
                                                       @PathVariable Long serieskey,
                                                       @PathVariable String filename) throws IOException {
        File imageFile = new File("Z:/" + File.separator + filename);
        if (!imageFile.exists()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + filename + "\"")
                .contentType(MediaType.IMAGE_JPEG)  // 필요한 경우 MediaType 변경
                .body(new FileSystemResource(imageFile));
    }

    @GetMapping("/dicom-images")
    public ResponseEntity<List<Image>> getDicomImages() {
        List<Image> dicomImages = imageService.getDicomImages();
        return new ResponseEntity<>(dicomImages, HttpStatus.OK);
    }

    @GetMapping("/{studykey}/{serieskey}")
    public ModelAndView getImagesByStudyKeyAndSeriesKey(@PathVariable Long studykey, @PathVariable Long serieskey) {
//        Map<Long, List<String>> seriesImages = imageService.getImageNamesByStudyKey(studykey);
//        List<String> images = seriesImages.getOrDefault(serieskey, new ArrayList<>());
//
//        ModelAndView mv = new ModelAndView("viewer/viewer");
//        mv.addObject("images", images);
        List<String> images = imageService.getDicomUrlsByStudyKeyAndSeriesKey(studykey, serieskey);
        System.out.println("images size : " + images.size());
        ModelAndView mv = new ModelAndView("viewer/viewer");
        mv.addObject("images", images);
        return mv;
    }

    @GetMapping("/{studykey}/{serieskey}/dicom-urls")
    @ResponseBody
    public ResponseEntity<List<String>> getDicomUrlsByStudyKeyAndSeriesKey(@PathVariable Long studykey, @PathVariable Long serieskey) {
        List<String> dicomUrls = imageService.getDicomUrlsByStudyKeyAndSeriesKey(studykey, serieskey);
        System.out.println("dicomUrls size : " + dicomUrls.size());
        return new ResponseEntity<>(dicomUrls, HttpStatus.OK);
    }

    @GetMapping("/dicom-file")
    @ResponseBody
    public ResponseEntity<FileSystemResource> getDicomFile(@RequestParam String path) {
        File file = new File(path);
        if (!file.exists()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + file.getName() + "\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(new FileSystemResource(file));
    }

//    @GetMapping("/list/page")
//    public ModelAndView getImageListWithPagination(@PageableDefault(size = 5) Pageable pageable) {
//        ModelAndView mv = new ModelAndView("image/list");
//        Page<Image> imagesPage = imageService.findAllWithPage((org.springframework.data.domain.Pageable) pageable);
//        mv.addObject("imagesPage", imagesPage);
//        return mv;
//    }

}
