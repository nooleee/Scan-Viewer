package com.pacs.scanviewer.pacs.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudyRepository extends JpaRepository<Study, Long> {

    Page<Study> findByPidContaining(String pid, Pageable pageable);
    Page<Study> findByPnameContaining(String pname, Pageable pageable);

    List<Study> findAllByPid(String pid);
}
