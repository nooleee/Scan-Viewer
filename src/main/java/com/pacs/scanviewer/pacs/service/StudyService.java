package com.pacs.scanviewer.pacs.service;

import com.pacs.scanviewer.pacs.domain.Image;
//import com.pacs.scanviewer.pacs.domain.ImageRepository;
import com.pacs.scanviewer.pacs.domain.Study;
import com.pacs.scanviewer.pacs.domain.StudyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class StudyService {

    private final StudyRepository studyRepository;
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

    public Page<Study> findByPidContaining(String pid, PageRequest pageRequest) {
        return studyRepository.findByPidContaining(pid, pageRequest);
    }

    public Page<Study> findByPnameContaining(String pname, PageRequest pageRequest) {
        return studyRepository.findByPnameContaining(pname, pageRequest);
    }

    public List<Study> getStudiesByPid(String pid) {
        return studyRepository.findAllByPid(pid);
    }

    public Page<Study> findByPidContainingAndPnameContaining(String pid, String pname, PageRequest pageRequest) {
        return studyRepository.findByPidContainingAndPnameContaining(pid, pname, pageRequest);
    }

//    public List<Image> findByStudykey(long studykey) {
//        return imageRepository.findAllById(studykey);
//    }
}
