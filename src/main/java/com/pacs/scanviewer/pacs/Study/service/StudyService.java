package com.pacs.scanviewer.pacs.Study.service;

//import com.pacs.scanviewer.pacs.Image.domain.ImageRepository;
import com.pacs.scanviewer.pacs.Study.domain.Study;
import com.pacs.scanviewer.pacs.Study.domain.StudyRepository;
import com.pacs.scanviewer.pacs.Study.domain.StudySpecification;
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

    public Page<Study> findStudiesWithPage(int page, int size) {
        return studyRepository.findAllByOrderByStudydateDesc(PageRequest.of(page, size));
    }

    public List<Study> getStudiesByPid(String pid) {
        return studyRepository.findAllByPid(pid);
    }


    public Page<Study> searchStudies(String pid, String pname, String startDate, String endDate, String modality, Pageable pageable) {
        return studyRepository.findAll(StudySpecification.searchStudies(pid, pname, startDate, endDate, modality), pageable);
    }


//    public List<Image> findByStudykey(long studykey) {
//        return imageRepository.findAllById(studykey);
//    }
}
