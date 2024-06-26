package com.pacs.scanviewer.pacs.service;

import com.pacs.scanviewer.pacs.domain.Image;
import com.pacs.scanviewer.pacs.domain.ImageRepository;
import com.pacs.scanviewer.pacs.domain.Study;
import com.pacs.scanviewer.pacs.domain.StudyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class StudyService {

    private final StudyRepository studyRepository;
    private final ImageRepository imageRepository;

    public List<Study> getAllStudies() {
        return studyRepository.findAll();
    }

    public List<Study> findAllWithPage(int page, Pageable pageable) {

        return studyRepository.findAll(pageable.withPage(page)).getContent();
    }

    public List<Image> findByStudykey(long studykey) {
        return imageRepository.findAllById(studykey);
    }
}
