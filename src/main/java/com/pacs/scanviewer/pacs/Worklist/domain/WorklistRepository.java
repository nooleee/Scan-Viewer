package com.pacs.scanviewer.pacs.Worklist.domain;

import com.pacs.scanviewer.pacs.Study.domain.Study;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorklistRepository extends JpaRepository<Study, Long> {
}
