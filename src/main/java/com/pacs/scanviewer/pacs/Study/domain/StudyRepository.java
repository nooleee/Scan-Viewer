package com.pacs.scanviewer.pacs.Study.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudyRepository extends JpaRepository<Study, Long> {

    List<Study> findAllByOrderByStudydateDesc();

    Page<Study> findAllByOrderByStudydateDesc(Pageable pageable);

    List<Study> findAllByPid(String pid);

    List<Study> findAllByStudykey(long studykey);
}
