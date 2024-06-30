package com.pacs.scanviewer.SCV.Consent.domain;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ConsentDto {

    private String userCode;
    private String name;
    private String birth;
    private String phone;
    private String position;
    private String address;
    private String department;
    private String user;
    private String detail;
    private String purpose;
    private String period;
    private int studyKey;
}
