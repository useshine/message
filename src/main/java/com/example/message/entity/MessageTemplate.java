package com.example.message.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import java.io.Serializable;

@Data
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class MessageTemplate implements Serializable {
    private Integer id;
    /*标题*/
    private String name;
    private Integer msgStatus;
    private Integer cronTaskId;
    /*定时发送时间*/
    private String expectPushTime;
    private Integer sendChannel;
    private String msgContent;
    private String creator;
    private String updator;
    private Integer createTime;
    /**
     * 更新时间 单位s
     */
    private Integer updateTime;
}
