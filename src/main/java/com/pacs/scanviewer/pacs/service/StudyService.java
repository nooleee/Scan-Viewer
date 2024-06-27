package com.pacs.scanviewer.pacs.service;

import com.pacs.scanviewer.pacs.domain.Image;
//import com.pacs.scanviewer.pacs.domain.ImageRepository;
import com.pacs.scanviewer.pacs.domain.ImageRepository;
import com.pacs.scanviewer.pacs.domain.Study;
import com.pacs.scanviewer.pacs.domain.StudyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class StudyService {

    private final StudyRepository studyRepository;
    private final ImageRepository imageRepository;
//    private final ImageRepository imageRepository;

    public List<Study> getAllStudies() {
        return studyRepository.findAll();
    }

    public List<Study> findAllWithPage(int page, Pageable pageable) {

        return studyRepository.findAll(pageable.withPage(page)).getContent();
    }

    public Page<Study> findStudiesWithPage(int page, int size) {
        return studyRepository.findAll(PageRequest.of(page, size));
    }

    public List<Study> getStudiesByPid(String pid) {
        return studyRepository.findAllByPid(pid);
    }

//    public Map<Long, List<String>> getImageNamesByStudyKey(Long studyKey) {
//        List<Image> imageList = imageRepository.findByStudykey(studyKey);
//        Map<Long, List<String>> seriesImages = new HashMap<>();
//
//        for (Image image : imageList) {
//            seriesImages.computeIfAbsent(image.getSerieskey(), k -> new java.util.ArrayList<>()).add(image.getPath() + image.getFname());
//        }
//
//        return seriesImages;
//    }


//    public List<Image> findByStudykey(long studykey) {
//        return imageRepository.findAllById(studykey);
//    }
}
