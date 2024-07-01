package com.pacs.scanviewer.pacs.Study.domain;

import org.springframework.data.jpa.domain.Specification;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class StudySpecification {

    public static Specification<Study> searchStudies(String pid, String pname, String startDate, String endDate, String modality) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (pid != null && !pid.isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("pid"), "%" + pid + "%"));
            }
            if (pname != null && !pname.isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("pname"), "%" + pname + "%"));
            }
            if (startDate != null && endDate != null) {
                predicates.add(criteriaBuilder.between(root.get("studydate"), startDate, endDate));
            }
            if (modality != null && !modality.isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("modality"), modality));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
