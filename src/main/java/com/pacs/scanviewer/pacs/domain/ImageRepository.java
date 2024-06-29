package com.pacs.scanviewer.pacs.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> findByStudykeyAndSerieskey(long studykey, long serieskey);

    @Query("SELECT DISTINCT i.serieskey FROM Image i WHERE i.studykey = :studykey")
    List<Long> findSeriesKeysByStudyKey(Long studykey);
}
