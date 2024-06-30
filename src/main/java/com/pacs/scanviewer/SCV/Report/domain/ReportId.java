package com.pacs.scanviewer.SCV.Report.domain;

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
                userCode.equals(reportId.userCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studyKey, userCode);
    }
}
