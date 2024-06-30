package com.pacs.scanviewer.SCV.Report.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.sql.Timestamp;

@IdClass(ReportId.class)
@Getter
@Setter
@Table(name = "report")
@Entity
public class Report {
    @Id
    private int studyKey;
    @Id
    private String userCode;

    private String content;
    private String patient;
    private String date;
    private String diseaseCode;

    @Enumerated(EnumType.STRING)
    private VideoReplay videoReplay;

    @CreatedDate
    private Timestamp regDate;
    @LastModifiedDate
    private Timestamp modDate;

    public enum VideoReplay {
        읽지않음, 판독불가, 판독완료
    }
}
