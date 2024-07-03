package com.pacs.scanviewer.pacs.Image.controller;

import com.pacs.scanviewer.pacs.Image.domain.Image;
import com.pacs.scanviewer.pacs.Image.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

    @GetMapping("/{studykey}/{index}")
    public ModelAndView getImagesByStudyKeyAndIndex(@PathVariable Long studykey, @PathVariable Integer index) {
        List<Long> seriesKeys = imageService.findSeriesKeysByStudyKey(studykey);
        Map<Integer, List<String>> indexedSeriesImages = seriesKeys.stream()
                .collect(Collectors.toMap(
                        key -> seriesKeys.indexOf(key) + 1,
                        key -> imageService.findByStudykeyAndSerieskey(studykey, key).stream()
                                .map(image -> "Z:/" + image.getPath() + image.getFname())
                                .collect(Collectors.toList())
                ));

        List<String> images = indexedSeriesImages.get(index);
        if (images == null) {
            return new ModelAndView("error/noSeriesFound");
        }

        List<Map<String, Object>> seriesList = createThumbnailList(studykey, seriesKeys);

        System.out.println("serieslist : " + seriesList);
        ModelAndView mv = new ModelAndView("viewer/viewer");
        mv.addObject("images", images);
        mv.addObject("seriesList", seriesList);
        return mv;
    }

    @GetMapping("/{studykey}/{serieskey}/dicom-urls")
    @ResponseBody
    public ResponseEntity<List<String>> getDicomUrlsByStudyKeyAndSeriesKey(@PathVariable Long studykey, @PathVariable Long serieskey) {
        List<Image> imageList = imageService.getDicomUrlsByStudyKeyAndSeriesKey(studykey, serieskey);
        List<String> dicomUrls = imageList.stream()
                .map(image -> "Z:/" + image.getPath() + image.getFname())
                .collect(Collectors.toList());

        return new ResponseEntity<>(dicomUrls, HttpStatus.OK);
    }

    @GetMapping("/dicom-file")
    @ResponseBody
    public ResponseEntity<FileSystemResource> getDicomFile(@RequestParam String path) throws IOException {
        String os = System.getProperty("os.name").toLowerCase();
        String sep = System.getProperty("file.separator");

        // URL 디코딩
        String decodedPath = URLDecoder.decode(path, StandardCharsets.UTF_8.name());

        String rootPath = "Z:\\STS\\";

        if (!os.contains("win")) {
            rootPath = "/Volumes/STS/";
            decodedPath = decodedPath.replace("Z:/",rootPath);
        }

        // 역슬래시를 슬래시로 변경
        decodedPath = decodedPath.replace("\\", sep);

        // 절대 경로인지 확인
        String fullPath;
        if (decodedPath.startsWith("Z:") || decodedPath.startsWith("/Volumes/STS")) {
            // 이미 절대 경로인 경우 rootPath를 추가하지 않음
            fullPath = decodedPath;
        } else {
            // 상대 경로인 경우 rootPath를 추가
            fullPath = rootPath + decodedPath;
        }

        // 전체 파일 경로 생성
        File file = new File(fullPath);

        // 디버깅을 위한 로그 추가
        System.out.println("Requested path: " + path);
        System.out.println("Decoded path: " + decodedPath);
        System.out.println("Full file path: " + file.getAbsolutePath());

        // 파일이 존재하지 않을 경우 404 응답
        if (!file.exists()) {
            System.out.println("File not found: " + file.getAbsolutePath()); // 파일이 존재하지 않을 경우 로그 추가
            return ResponseEntity.notFound().build();
        }

        // 파일이 존재할 경우 파일 반환
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + file.getName() + "\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(new FileSystemResource(file));
    }


    private List<Map<String, Object>> createThumbnailList(Long studykey, List<Long> seriesKeys) {
        return seriesKeys.stream()
                .map(key -> {
                    List<Image> imagesForSeries = imageService.findByStudykeyAndSerieskey(studykey, key);
                    String thumbnailUrl = "Z:/" + imagesForSeries.get(0).getPath() + imagesForSeries.get(0).getFname();
                    System.out.println("thumbnailUrl: " + thumbnailUrl);
                    Map<String, Object> seriesMap = new HashMap<>();
                    seriesMap.put("seriesKey", key.toString());
                    seriesMap.put("thumbnailUrl", thumbnailUrl);
                    seriesMap.put("index", seriesKeys.indexOf(key) + 1);
                    return seriesMap;
                })
                .collect(Collectors.toList());
    }
}
