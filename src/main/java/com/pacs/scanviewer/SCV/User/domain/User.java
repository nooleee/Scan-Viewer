package com.pacs.scanviewer.SCV.User.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;


@NoArgsConstructor
@Getter
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)
@Entity
public class User implements UserDetails {

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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null; // 권한 관리를 원하면 추가합니다.
    }

    @Override
    public String getUsername() {

        return this.userCode;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
