package com.example.message.service;

import com.example.message.mapper.IMapper;
import org.springframework.beans.factory.annotation.Autowired;

public class IService<T> {

    @Autowired
    private IMapper<T> iMapper;

    public T Get(Integer id){
        return iMapper.Get(id);
    }

    public void Add(T t){
         iMapper.Add(t);
    }

    public void Update(T t){
        iMapper.Update(t);
    }

    public void Delete(Integer id){
        iMapper.Delete(id);
    }
}
