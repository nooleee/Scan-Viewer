package com.pacs.scanviewer.SCV.Report.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Timestamp;

@IdClass(ReportId.class)
@Getter
@Setter
@Table(name = "report")
@Entity
@EntityListeners(AuditingEntityListener.class)
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
    @Column(updatable = false)
    private Timestamp regDate;
    @LastModifiedDate
    private Timestamp modDate;

    public enum VideoReplay {
        읽지않음, 판독취소, 판독완료
    }
}
