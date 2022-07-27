package com.example.message.dto;

import com.example.message.entity.Account;
import lombok.Data;

import java.util.Date;

@Data
public class LoginDto {
    private Integer id;
    private String username;
    private Integer type;
    private String token;
    private Date tokenValidPeriod;
    private Date loginTime;

    public LoginDto() {
    }

    public LoginDto(Account account) {
        this.loginTime = new Date();
        this.tokenValidPeriod = account.getTokenValidPeriod();
        this.id = account.getId();
        this.username = account.getUsername();
        this.type = account.getType();
        this.token = account.getToken();
    }

}

