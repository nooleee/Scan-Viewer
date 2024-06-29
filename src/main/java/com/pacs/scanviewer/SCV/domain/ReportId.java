package com.pacs.scanviewer.SCV.domain;

import com.pacs.scanviewer.pacs.domain.ImageId;
import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

@Data
public class ReportId implements Serializable {
    private int studyKey;
    private String userCode;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReportId reportId = (ReportId) o;
        return studyKey == reportId.studyKey &&
                userCode == reportId.userCode;
    }

    @Override
    public int hashCode() {
        return Objects.hash(studyKey, userCode);
    }
}
