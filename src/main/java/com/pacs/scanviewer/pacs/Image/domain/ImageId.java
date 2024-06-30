package com.pacs.scanviewer.pacs.Image.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

@Data
public class ImageId implements Serializable {
    private long studykey;
    private long serieskey;
    private long imagekey;

    // equals와 hashCode를 반드시 구현해야 합니다.
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ImageId imageId = (ImageId) o;
        return studykey == imageId.studykey &&
                serieskey == imageId.serieskey &&
                imagekey == imageId.imagekey;
    }

    @Override
    public int hashCode() {
        return Objects.hash(studykey, serieskey, imagekey);
    }
}
