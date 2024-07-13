package com.pacs.scanviewer.pacs.Image.controller;

import com.pacs.scanviewer.pacs.Image.domain.Image;
import com.pacs.scanviewer.pacs.Image.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequestMapping("/images")
@RequiredArgsConstructor
@Controller
public class ImageController {

    private final ImageService imageService;

    @GetMapping("/dicom-images")
    public ResponseEntity<List<Image>> getDicomImages() {
        List<Image> allImages = imageService.getDicomImages();
        List<Image> dicomImages = allImages.stream()
                .filter(image -> image.getFname().endsWith(".dcm"))
                .collect(Collectors.toList());
        return new ResponseEntity<>(dicomImages, HttpStatus.OK);
    }

    @GetMapping("/{studykey}/{serieskey}")
    public ModelAndView getImagesByStudyKeyAndSeriesKey(@PathVariable Long studykey, @PathVariable Long serieskey) {
        List<Long> seriesKeys = imageService.findSeriesKeysByStudyKey(studykey);
        Map<Long, List<String>> allSeriesImages = new HashMap<>();
        if (seriesKeys.isEmpty()) {
            return new ModelAndView("redirect:/worklist");
        }

        List<Map<String, Object>> thseriesList = createThumbnailList(studykey, seriesKeys);

        for (Long seriesKey : seriesKeys) {
            List<String> images = imageService.findByStudykeyAndSerieskey(studykey, seriesKey).stream()
                    .map(image -> formatPathForOS("Z:/" + image.getPath() + image.getFname()))
                    .collect(Collectors.toList());

            allSeriesImages.put(seriesKey, images);

            System.out.println("[DEBUG] seriesKey: " + seriesKey + ", images: " + images); // 디버그 로그 추가
        }

        System.out.println("serieslist : " + thseriesList);
        ModelAndView mv = new ModelAndView("viewer/viewer");
        mv.addObject("images", allSeriesImages);
        mv.addObject("seriesList", thseriesList);
        return mv;
    }

    private List<Map<String, Object>> createThumbnailList(Long studykey, List<Long> seriesKeys) {
        return seriesKeys.stream()
                .map(key -> {
                    List<Image> imagesForSeries = imageService.findByStudykeyAndSerieskey(studykey, key);
                    String thumbnailUrl = "Z:/" + imagesForSeries.get(0).getPath() + imagesForSeries.get(0).getFname();
                    Map<String, Object> seriesMap = new HashMap<>();
                    seriesMap.put("seriesKey", key.toString());
                    seriesMap.put("thumbnailUrl", thumbnailUrl);
                    seriesMap.put("index", seriesKeys.indexOf(key) + 1);
                    return seriesMap;
                })
                .collect(Collectors.toList());
    }

    @GetMapping("/studies")
    public ModelAndView getImagesByStudyKey(@RequestParam Long studykey) {
        List<Long> seriesKeys = imageService.findSeriesKeysByStudyKey(studykey);
        if (seriesKeys.isEmpty()) {
            return new ModelAndView("redirect:/worklist");
        }

        List<Map<String, Object>> seriesList = createThumbnailList(studykey, seriesKeys);

        System.out.println("serieslist : " + seriesList);
        ModelAndView mv = new ModelAndView("viewer/viewer");
        mv.addObject("seriesList", seriesList);
        return mv;
    }



    private String formatPathForOS(String path) {
        String os = System.getProperty("os.name").toLowerCase();
        String sep = System.getProperty("file.separator");

        String rootPath = "Z:\\STS\\";

        if (!os.contains("win")) {
            rootPath = "/Volumes/STS/";
            path = path.replace("Z:/", rootPath);
        }

        return path.replace("\\", sep);
    }
}
