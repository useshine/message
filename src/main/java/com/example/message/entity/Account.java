package com.example.message.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    private Integer id;
    private String username;
    private String password;
    private Integer type;
    private String token;
    private Date createTime;
    private Date tokenValidPeriod;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Integer getId() {
        return id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public Integer getType() {
        return type;
    }

    public Date getTokenValidPeriod() {
        return tokenValidPeriod;
    }

    public String getToken() {
        return token;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setTokenValidPeriod(Date tokenValidPeriod) {
        this.tokenValidPeriod = tokenValidPeriod;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
