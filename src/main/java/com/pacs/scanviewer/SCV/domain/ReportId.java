package com.pacs.scanviewer.SCV.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class ReportId implements Serializable {
    private int studyKey;
    private String userCode;
}
