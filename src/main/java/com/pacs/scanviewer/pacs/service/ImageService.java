package com.pacs.scanviewer.pacs.service;

import com.pacs.scanviewer.pacs.domain.Image;
import com.pacs.scanviewer.pacs.domain.ImageRepository;
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
}
