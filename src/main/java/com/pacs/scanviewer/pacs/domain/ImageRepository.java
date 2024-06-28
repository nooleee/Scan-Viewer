package com.pacs.scanviewer.pacs.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> findByStudykeyAndSerieskey(long studykey, long serieskey);
}
