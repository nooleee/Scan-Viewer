package com.pacs.scanviewer.pacs.Study.service;

import com.pacs.scanviewer.pacs.Study.domain.Study;
import com.pacs.scanviewer.pacs.Study.domain.StudyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class StudyService {

    private final StudyRepository studyRepository;

    public List<Study> findByStudykey(long studykey) {
        return studyRepository.findAllByStudykey(studykey);
    }
}
