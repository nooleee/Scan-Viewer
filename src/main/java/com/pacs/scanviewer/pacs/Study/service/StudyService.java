package com.pacs.scanviewer.pacs.Study.service;

//import com.pacs.scanviewer.pacs.Image.domain.ImageRepository;
import com.pacs.scanviewer.pacs.Study.domain.Study;
import com.pacs.scanviewer.pacs.Study.domain.StudyRepository;
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

    public List<Study> getStudiesByPid(String pid) {
        return studyRepository.findAllByPid(pid);
    }

    public List<Study> findByStudykey(long studykey) {
        return studyRepository.findAllByStudykey(studykey);
    }

    public Page<Study> findByPidContainingAndDateRange(String pid, String startDate, String endDate, PageRequest pageRequest) {
        return studyRepository.findByPidContainingAndStudydateBetweenOrderByStudydateDesc(pid, startDate, endDate, pageRequest);
    }

    public Page<Study> findByPnameContainingAndDateRange(String pname, String startDate, String endDate, PageRequest pageRequest) {
        return studyRepository.findByPnameContainingAndStudydateBetweenOrderByStudydateDesc(pname, startDate, endDate, pageRequest);
    }

    public Page<Study> findByPidPnameAndDateRange(String pid, String pname, String startDate, String endDate, PageRequest pageRequest) {
        return studyRepository.findByPidContainingAndPnameContainingAndStudydateBetweenOrderByStudydateDesc(pid, pname, startDate, endDate, pageRequest);
    }

    public Page<Study> findByDateRange(String startDate, String endDate, PageRequest pageRequest) {
        return studyRepository.findByStudydateBetweenOrderByStudydateDesc(startDate, endDate, pageRequest);
    }



//    public List<Image> findByStudykey(long studykey) {
//        return imageRepository.findAllById(studykey);
//    }
}
