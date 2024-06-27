package com.pacs.scanviewer.SCV.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@NoArgsConstructor
@Table(name = "consent_log")
@EntityListeners(AuditingEntityListener.class)
@Entity
public class Consent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int accessLog;

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

    @CreatedDate
    private Timestamp regDate;

    public Consent(ConsentDto consentDto) {
        if (consentDto.getUserCode() != null) {
            this.userCode = consentDto.getUserCode();
        }
        if (consentDto.getName() != null) {
            this.name = consentDto.getName();
        }
        if (consentDto.getBirth() != null) {
            this.birth = consentDto.getBirth();
        }
        if (consentDto.getPhone() != null) {
            this.phone = consentDto.getPhone();
        }
        if (consentDto.getPosition() != null) {
            this.position = consentDto.getPosition();
        }
        if (consentDto.getAddress() != null) {
            this.address = consentDto.getAddress();
        }
        if (consentDto.getDepartment() != null) {
            this.department = consentDto.getDepartment();
        }
        if (consentDto.getUser() != null) {
            this.user = consentDto.getUser();
        }
        if (consentDto.getDetail() != null) {
            this.detail = consentDto.getDetail();
        }
        if (consentDto.getPurpose() != null) {
            this.purpose = consentDto.getPurpose();
        }
        if (consentDto.getPeriod() != null) {
            this.period = consentDto.getPeriod();
        }
        if (consentDto.getStudyKey() != 0) { // assuming 0 is not a valid studyKey
            this.studyKey = consentDto.getStudyKey();
        }
    }
}
