package com.example.message.service;

import com.example.message.mapper.AccountMapper;
import com.example.message.entity.Account;
import com.example.message.utils.DateUtils;
import com.example.message.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;
import java.util.UUID;

@Service
public class TokenService {

    @Autowired
    private AccountMapper accountMapper;

    public Account createToken(Account account) {
        String token = String.valueOf(UUID.randomUUID());
        account.setToken(token);
        account.setTokenValidPeriod(DateUtils.createTokenPeriod());
        return account;
    }

    public Account getAccountByToken(String token) {
        Account account = null;
        try {
            if (StringUtils.isNotEmpty(token)) {
                account = accountMapper.GetByToken(token);
            }
        } catch (Exception e) {
        }
        return account;
    }

    public boolean isValid(Date  tokenValidPeriod) throws ParseException {
        Date tokenPeriod =tokenValidPeriod;
        if(null == tokenPeriod) return false;
        return DateUtils.tokenIsValid(tokenPeriod);
    }

}