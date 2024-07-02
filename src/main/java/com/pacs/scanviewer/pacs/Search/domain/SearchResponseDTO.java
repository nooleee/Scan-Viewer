package com.pacs.scanviewer.pacs.Search.domain;

public class SearchResponseDTO {
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

    // Getters and Setters
    public long getStudykey() {
        return studykey;
    }

    public void setStudykey(long studykey) {
        this.studykey = studykey;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getModality() {
        return modality;
    }

    public void setModality(String modality) {
        this.modality = modality;
    }

    public String getStudydesc() {
        return studydesc;
    }

    public void setStudydesc(String studydesc) {
        this.studydesc = studydesc;
    }

    public String getStudydate() {
        return studydate;
    }

    public void setStudydate(String studydate) {
        this.studydate = studydate;
    }

    public long getReportstatus() {
        return reportstatus;
    }

    public void setReportstatus(long reportstatus) {
        this.reportstatus = reportstatus;
    }

    public long getSeriescnt() {
        return seriescnt;
    }

    public void setSeriescnt(long seriescnt) {
        this.seriescnt = seriescnt;
    }

    public long getImagecnt() {
        return imagecnt;
    }

    public void setImagecnt(long imagecnt) {
        this.imagecnt = imagecnt;
    }

    public long getExamstatus() {
        return examstatus;
    }

    public void setExamstatus(long examstatus) {
        this.examstatus = examstatus;
    }
}
