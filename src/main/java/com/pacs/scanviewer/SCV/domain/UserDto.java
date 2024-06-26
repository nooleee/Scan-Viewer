package com.pacs.scanviewer.SCV.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;


@NoArgsConstructor
@Setter
@Getter
public class UserDto {
    private String userCode;
    private String password;
    private String name;
    private String phone;
    private String birth;
    private String group;
}
