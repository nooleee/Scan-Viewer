package com.pacs.scanviewer.pacs.Worklist.domain;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Table(name="V_STUDYTAB", schema = "PACSPLUS")
@Entity
public class Worklist {

    @Id
    private long studykey;
    private String pid;
    private String pname;
    private String modality;
    private String studydesc;
    private String studydate;
    private long reportstatus;
    private long seriescnt;
    private long imagecnt;
    private long examstatus;
}
