package com.pacs.scanviewer.SCV.StudyLog.domain;


import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Timestamp;

@EntityListeners(AuditingEntityListener.class)
@Getter
@NoArgsConstructor
@Entity (name = "study_log")
public class StudyLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int studyLog;

    private int studyKey;
    private String userCode;
    @CreationTimestamp
    private Timestamp regDate;

    public StudyLog(int studykey, String userCode) {
        this.studyKey = studykey;
        this.userCode = userCode;
    }
}
