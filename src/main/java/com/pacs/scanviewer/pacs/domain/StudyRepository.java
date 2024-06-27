package com.pacs.scanviewer.pacs.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudyRepository extends JpaRepository<Study, Long> {

    List<Study> findAllByPid(String pid);
}
