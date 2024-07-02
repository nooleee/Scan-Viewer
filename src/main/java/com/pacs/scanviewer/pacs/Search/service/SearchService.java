package com.pacs.scanviewer.pacs.Search.service;

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

@RequiredArgsConstructor
@Service
public class SearchService {


    private final StudyRepository studyRepository;

    public Page<SearchResponseDTO> searchStudies(SearchRequestDTO searchDTO, Pageable pageable) {
        Pageable sortedPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Direction.DESC, "studydate"));
        return studyRepository.findAll(SearchSpecification.searchStudies(searchDTO), sortedPageable)
                .map(this::convertToDTO);
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
