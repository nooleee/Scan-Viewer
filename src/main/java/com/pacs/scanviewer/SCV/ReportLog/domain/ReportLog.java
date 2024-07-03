package com.pacs.scanviewer.SCV.ReportLog.domain;

import com.pacs.scanviewer.SCV.Report.domain.Report;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@Table(name = "report_log")
@Entity
@EntityListeners(AuditingEntityListener.class)
public class ReportLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int logCode;

    private int studyKey;
    private String userCode;
    private String content;
    private String patient;
    private String date;
    private String diseaseCode;

    @Enumerated(EnumType.STRING)
    private Report.VideoReplay videoReplay;

    @CreatedDate
    @Column(updatable = false)
    private Timestamp regDate;
}
