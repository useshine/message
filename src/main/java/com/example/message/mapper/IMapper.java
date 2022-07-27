package com.example.message.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IMapper<T> {
    T Get(Integer id);
    void Add(T t);
    void Update(T t);
    void Delete(Integer id);
}
