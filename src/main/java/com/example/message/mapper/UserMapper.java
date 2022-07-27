package com.example.message.mapper;

import com.example.message.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper extends IMapper<User> {
    User GetByName(String name);

    List<User> GetUserByMsgId(Integer id);

    List<User> GetAll();
}
