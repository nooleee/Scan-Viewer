package com.pacs.scanviewer.SCV.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@Table(name = "report")
@Entity
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int studyKey;

    private String userCode;

    private String content;
    private String patient;
    private String date;
    private String diseaseCode;

    @Enumerated(EnumType.STRING)
    private String videoReplay;

    private Timestamp regDate;
    private Timestamp modDate;

    public enum String {
        읽지않음, 판독불가, 판독완료
    }
}
