package com.pacs.scanviewer.SCV.User.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


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

    public UserDto(User user) {
        if (user != null) {
            this.userCode = user.getUserCode();
        }
        if (user.getPassword() != null) {
            this.password = user.getPassword();
        }
        if (user.getName() != null) {
            this.name = user.getName();
        }
        if (user.getPhone() != null) {
            this.phone = user.getPhone();
        }
        if (user.getBirth() != null) {
            this.birth = user.getBirth();
        }
        if (user.getGroup() != null) {
            this.group = user.getGroup();
        }



    }
}
