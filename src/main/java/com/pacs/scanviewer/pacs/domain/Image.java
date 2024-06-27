package com.pacs.scanviewer.pacs.domain;


import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Getter
@Table(name = "V_IMAGETAB", schema = "PACSPLUS")
@Entity
public class Image {

    @Id
    private long studykey;
    private long serieskey;
    private long imagekey;
    private String path;
    private String fname;

    public String getFormattedPath() {
        return path.replace("\\", "/");
    }



}
