package com.pacs.scanviewer.SCV.User.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Timestamp;


@NoArgsConstructor
@Getter
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)
@Entity
public class User {

    @Id
    @Column(name = "userCode")
    private String userCode;

    private String password;
    private String name;
    private String phone;
    private String birth;
    @Column(name = "`group`")
    private String group;
    @CreatedDate
    private Timestamp regDate;
    @LastModifiedDate
    private Timestamp modDate;

    public User(UserDto userDto){
        if(userDto.getUserCode()!=null){
            this.userCode = userDto.getUserCode();
        }

        if(userDto.getPassword()!=null){
            this.password = userDto.getPassword();
        }
        if(userDto.getName()!=null){
            this.name = userDto.getName();
        }
        if(userDto.getPhone()!=null){
            this.phone = userDto.getPhone();
        }
        if(userDto.getBirth()!=null){
            this.birth = userDto.getBirth();
        }
        if(userDto.getGroup()!=null){
            this.group = userDto.getGroup();
        }

    }
}
