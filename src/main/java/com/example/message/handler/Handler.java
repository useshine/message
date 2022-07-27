package com.example.message.handler;

import com.example.message.common.model.TaskInfo;

public interface Handler {
    boolean doHandler(TaskInfo taskInfo);
}
