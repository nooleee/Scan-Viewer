package com.pacs.scanviewer.pacs.Search.service;

import com.pacs.scanviewer.SCV.Consent.domain.Consent;
import com.pacs.scanviewer.SCV.Consent.domain.ConsentRepository;
import com.pacs.scanviewer.SCV.Report.domain.Report;
import com.pacs.scanviewer.SCV.Report.domain.ReportRepository;
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

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class SearchService {


    private final StudyRepository studyRepository;
    private final ConsentRepository consentRepository;
    private final ReportRepository reportRepository;

    public Page<SearchResponseDTO> searchStudies(SearchRequestDTO searchDTO, Pageable pageable) {
        Pageable sortedPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Direction.DESC, "studydate"));
        Page<Study> studiesPage = studyRepository.findAll(SearchSpecification.searchStudies(searchDTO), sortedPageable);

        return studiesPage.map(this::convertToDTO);
    }

//    public Page<SearchResponseDTO> searchStudies(SearchRequestDTO searchDTO, Pageable pageable) {
//        Pageable sortedPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Direction.DESC, "studydate"));
//        Page<Study> studyPage = studyRepository.findAll(SearchSpecification.searchStudies(searchDTO), sortedPageable);
//
//        List<SearchResponseDTO> dtos = studyPage.getContent().stream()
//                .map(this::convertToDTO)
//                .collect(Collectors.toList());
//
//        if (searchDTO.getReportStatus() != null) {
//            dtos = dtos.stream()
//                    .filter(dto -> {
//                        Optional<Report> reportOptional = reportRepository.findReportByStudyKey((int)dto.getStudykey());
//                        if (reportOptional.isPresent()) {
//                            Report report = reportOptional.get();
//                            if (report.getVideoReplay().equals(Report.VideoReplay.판독완료) && searchDTO.getReportStatus() == 2) {
//                                return true;
//                            } else if (report.getVideoReplay().equals(Report.VideoReplay.판독취소) && searchDTO.getReportStatus() == 1) {
//                                return true;
//                            }
//                        } else {
//                            return searchDTO.getReportStatus() == 0;
//                        }
//                        return false;
//                    })
//                    .collect(Collectors.toList());
//        }
//
//        return new PageImpl<>(dtos, sortedPageable, studyPage.getTotalElements());
//    }

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

                    Optional<Report> reportOptional = reportRepository.findReportByStudyKey((int)study.getStudykey());
                    if (reportOptional.isPresent()) {
                        Report report = reportOptional.get();
                        if(report.getVideoReplay().equals(Report.VideoReplay.판독완료)){
                            dto.setReportstatus(2);
                        }else{
                            dto.setReportstatus(1);
                        }
                    }else{
                        dto.setReportstatus(0);
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
        dto.setAiScore(study.getAi_score());
        dto.setAiFinding(study.getAi_finding());
        return dto;
    }
}
