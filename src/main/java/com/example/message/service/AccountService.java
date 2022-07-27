package com.example.message.service;

import com.example.message.common.exception.BusException;
import com.example.message.mapper.AccountMapper;
import com.example.message.entity.Account;
import com.example.message.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService extends IService<Account> {
    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private TokenService tokenService;

    public Account Login(String username, String password) {
        // 用户名或密码为空 错误
        if (StringUtils.isAnyBlank(username, password)) {
            throw new BusException("用户/密码必须填写");
        }
        // 密码如果不在指定范围内 错误
        if (password.length() < 0 || password.length() > 255) {
            throw new BusException("用户密码不在指定范围");
        }
        // 用户名不在指定范围内 错误
        if (username.length() < 0 || username.length() > 255) {
            throw new BusException("用户名不在指定范围");
        }
        Account account = GetAccountByUsername(username);
        if (StringUtils.isNull(account)) {
            throw new BusException("登录用户：" + username + " 不存在");
        } else {
            if (!account.getPassword().equals(password)){
                throw new BusException("登录用户：" + username + "用户密码错误");
            }
        }
        tokenService.createToken(account);
        Update(account);
        return account;
    }



    public Account GetAccountByUsername(String username) {
        return accountMapper.GetAccountByUsername(username);
    }

    public Account GetByToken(String token) {
        return accountMapper.GetByToken(token);
    }

}
