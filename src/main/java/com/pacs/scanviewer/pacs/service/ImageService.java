package com.pacs.scanviewer.pacs.service;

import com.pacs.scanviewer.pacs.domain.Image;
import com.pacs.scanviewer.pacs.domain.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
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
}
