package com.pacs.scanviewer.pacs.service;

import com.pacs.scanviewer.pacs.domain.Image;
import com.pacs.scanviewer.pacs.domain.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ImageService {
    @Autowired
    private ImageRepository imageRepository;


    public List<Image> getAllImages() {
        return imageRepository.findAll();
    }

//    public Page<Image> findAllWithPage(Pageable pageable) {
//        return imageRepository.findAll(pageable);
//    }

    public List<Image> getDicomImages() {
        List<Image> allImages = imageRepository.findAll();
        return allImages.stream()
                .filter(image -> image.getFname().endsWith(".dcm"))
                .collect(Collectors.toList());
    }

    public List<Image> getImagesByStudyKeyAndSeriesKey(Long studykey, Long serieskey) {
        return imageRepository.findByStudykeyAndSerieskey(studykey, serieskey);
    }

    public Map<Long, List<String>> getImageNamesByStudyKey(Long studyKey) {
        List<Image> imageList = imageRepository.findByStudykey(studyKey);
        Map<Long, List<String>> seriesImages = new HashMap<>();

        for (Image image : imageList) {
            String fullPath = "Z:/" + image.getFormattedPath() + image.getFname();
            seriesImages.computeIfAbsent(image.getSerieskey(), k -> new java.util.ArrayList<>()).add(fullPath);
        }

        return seriesImages;
    }

    public List<String> getDicomUrlsByStudyKeyAndSeriesKey(Long studykey, Long serieskey) {
        List<Image> imageList = imageRepository.findByStudykeyAndSerieskey(studykey, serieskey);
        return imageList.stream()
                .map(image -> "Z:/" + image.getPath() + image.getFname())
                .collect(Collectors.toList());
    }
}
