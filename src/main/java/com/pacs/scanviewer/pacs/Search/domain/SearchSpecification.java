package com.pacs.scanviewer.pacs.Search.domain;

import com.pacs.scanviewer.pacs.Search.domain.SearchRequestDTO;
import com.pacs.scanviewer.pacs.Study.domain.Study;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class SearchSpecification {

    public static Specification<Study> searchStudies(SearchRequestDTO searchDTO) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (searchDTO.getPid() != null && !searchDTO.getPid().isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("pid"), "%" + searchDTO.getPid() + "%"));
            }
            if (searchDTO.getPname() != null && !searchDTO.getPname().isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("pname"), "%" + searchDTO.getPname() + "%"));
            }
            if (searchDTO.getStartDate() != null && searchDTO.getEndDate() != null) {
                predicates.add(criteriaBuilder.between(root.get("studydate"), searchDTO.getStartDate(), searchDTO.getEndDate()));
            }
            if (searchDTO.getModality() != null && !searchDTO.getModality().isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("modality"), searchDTO.getModality()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
