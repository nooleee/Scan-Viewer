package com.pacs.scanviewer.pacs.Search.domain;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SearchRequestDTO {
    private String pid;
    private String pname;
    private String startDate;
    private String endDate;
    private String modality;
    private Integer reportStatus;
    private long reportstatus;
    private Float ai_score;
    private String ai_finding;
}
