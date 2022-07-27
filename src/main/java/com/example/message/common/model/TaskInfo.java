package com.example.message.common.model;

import com.example.message.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskInfo {

    private Integer messageTemplateId;
    private List<User> receiver;
    private Integer sendChannel;
    private String content;
    private String title;
}
