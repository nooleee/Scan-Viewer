package com.pacs.scanviewer.pacs.Image.domain;


import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@IdClass(ImageId.class)
@Getter
@Table(name = "V_IMAGETAB", schema = "PACSPLUS")
@Entity
public class Image {

    @Id
    private long studykey;
    @Id
    private long serieskey;
    @Id
    private long imagekey;
    private String path;
    private String fname;

    public String getFormattedPath() {
        return path.replace("\\", "/");
    }



}
