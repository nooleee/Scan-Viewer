package com.pacs.scanviewer.pacs.Search.service;

import com.pacs.scanviewer.SCV.Consent.domain.Consent;
import com.pacs.scanviewer.SCV.Consent.domain.ConsentRepository;
import com.pacs.scanviewer.pacs.Search.domain.SearchSpecification;
import com.pacs.scanviewer.pacs.Search.domain.SearchRequestDTO;
import com.pacs.scanviewer.pacs.Search.domain.SearchResponseDTO;
import com.pacs.scanviewer.pacs.Study.domain.Study;
import com.pacs.scanviewer.pacs.Study.domain.StudyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class SearchService {


    private final StudyRepository studyRepository;
    private final ConsentRepository consentRepository;

    public Page<SearchResponseDTO> searchStudies(SearchRequestDTO searchDTO, Pageable pageable) {
        Pageable sortedPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Direction.DESC, "studydate"));
        return studyRepository.findAll(SearchSpecification.searchStudies(searchDTO), sortedPageable)
                .map(this::convertToDTO);
    }

    public List<SearchResponseDTO> findStudiesByPid(String pid, String userCode) {
        List<Study> studies = studyRepository.findAllByPid(pid);
        return studies.stream()
                .map(study -> {
                    SearchResponseDTO dto = convertToDTO(study);
                    Optional<Consent> consent = consentRepository.findByStudyKeyAndUserCode((int) study.getStudykey(), userCode);
                    if (consent.isPresent()) {
                        dto.setExamstatus(1);
                    } else {
                        dto.setExamstatus(0);
                    }
                    return dto;
                })
                .collect(Collectors.toList());
    }

    private SearchResponseDTO convertToDTO(Study study) {
        SearchResponseDTO dto = new SearchResponseDTO();
        dto.setStudykey(study.getStudykey());
        dto.setPid(study.getPid());
        dto.setPname(study.getPname());
        dto.setModality(study.getModality());
        dto.setStudydesc(study.getStudydesc());
        dto.setStudydate(study.getStudydate());
        dto.setReportstatus(study.getReportstatus());
        dto.setSeriescnt(study.getSeriescnt());
        dto.setImagecnt(study.getImagecnt());
        dto.setExamstatus(study.getExamstatus());
        return dto;
    }
}
