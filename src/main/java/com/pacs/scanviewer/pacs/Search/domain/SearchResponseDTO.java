package com.pacs.scanviewer.pacs.Search.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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
    private Float ai_score;
    private String ai_finding;


    // 추가: null일 때 기본 값으로 설정
    public void setAiScore(Float ai_score) {
        this.ai_score = ai_score != null ? ai_score : 0.0f;
    }

    public void setAiFinding(String ai_finding) {
        this.ai_finding = ai_finding != null ? ai_finding : "";
    }
}
