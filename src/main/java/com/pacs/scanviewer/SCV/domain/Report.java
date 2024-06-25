package com.pacs.scanviewer.SCV.domain;

import lombok.Getter;

import java.sql.Timestamp;

@Getter
@Table(name = "report")
@Entity
public class Report {
    @Id
    private int studyKey;
    private String userCode;
    private String content;
    private String patient;
    private String date;
    private String diseaseCode;
    private String videoReplay;
    private Timestamp regDate;
    private Timestamp modDate;
}
