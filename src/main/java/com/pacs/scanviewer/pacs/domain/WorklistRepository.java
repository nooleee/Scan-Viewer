package com.pacs.scanviewer.pacs.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface WorklistRepository extends JpaRepository<Study, Long> {
}
