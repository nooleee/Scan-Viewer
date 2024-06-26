package com.pacs.scanviewer.pacs.domain;

import com.pacs.scanviewer.pacs.domain.Image;
import com.pacs.scanviewer.pacs.domain.ImageId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, ImageId> {
    List<Image> findByStudykey(long studykey);
}
