package com.example.message.handler;

import com.example.message.common.model.TaskInfo;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

public abstract class BaseHandler implements Handler {
    @Autowired
    private HandlerHolder handlerHolder;

    protected Integer channelCode;

    @PostConstruct
    private void init(){
        handlerHolder.putHandler(channelCode,this);
    }

    public  abstract  boolean handler(TaskInfo taskInfo);

}
