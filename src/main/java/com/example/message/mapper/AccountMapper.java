package com.example.message.mapper;

import com.example.message.entity.Account;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AccountMapper extends IMapper<Account> {
    Account GetAccountByUsername(String username);

    Account GetByToken(String token);
}
