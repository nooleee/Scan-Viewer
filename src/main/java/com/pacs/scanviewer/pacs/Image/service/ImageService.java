package com.pacs.scanviewer.pacs.Image.service;

import com.pacs.scanviewer.pacs.Image.domain.Image;
import com.pacs.scanviewer.pacs.Image.domain.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ImageService {

    private final ImageRepository imageRepository;

    public List<Image> getDicomImages() {
        return imageRepository.findAll();
    }

    public List<Image> getDicomUrlsByStudyKeyAndSeriesKey(Long studykey, Long serieskey) {
        return imageRepository.findByStudykeyAndSerieskey(studykey, serieskey);
    }

    public List<Long> getSeriesKeysByStudyKey(Long studykey) {
        return imageRepository.findSeriesKeysByStudyKey(studykey);
    }
}
