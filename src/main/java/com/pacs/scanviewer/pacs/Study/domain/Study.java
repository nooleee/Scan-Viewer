package com.pacs.scanviewer.pacs.Study.domain;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Table(name = "STUDYTAB", schema="PACSPLUS")
@Entity
public class Study {

    @Id
    private long studykey;
    private String pid;
    private String pname;
    private String modality;
    private String studydesc;
    private String studydate;
    private long reportStatus;
    private long seriescnt;
    private long imagecnt;
    private long examstatus;
    private Float ai_score;
    private String ai_finding;
}
