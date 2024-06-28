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
        return studyRepository.findAllByOrderByStudydateDesc(PageRequest.of(page, size));
    }

    public Page<Study> findByPidContaining(String pid, PageRequest pageRequest) {
        return studyRepository.findByPidContainingOrderByStudydateDesc(pid, pageRequest);
    }

    public Page<Study> findByPnameContaining(String pname, PageRequest pageRequest) {
        return studyRepository.findByPnameContainingOrderByStudydateDesc(pname, pageRequest);
    }

    public List<Study> getStudiesByPid(String pid) {
        return studyRepository.findAllByPid(pid);
    }

    public Page<Study> findByPidContainingAndPnameContaining(String pid, String pname, PageRequest pageRequest) {
        return studyRepository.findByPidContainingAndPnameContainingOrderByStudydateDesc(pid, pname, pageRequest);
    }

    public List<Study> findByStudykey(long studykey) {
        return studyRepository.findAllByStudykey(studykey);
    }
//    public List<Image> findByStudykey(long studykey) {
//        return imageRepository.findAllById(studykey);
//    }
}
