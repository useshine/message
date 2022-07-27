package com.example.message.common.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public enum ChannelType {
    SMS(10, "sms(短信)","sms"),
    EMAIL(20, "email(邮件)", "email"),
    ENTERPRISE_WE_CHAT(30, "EnterpriseWeChat(企业微信)", "enterprise_we_chat"),
    DING_DING_ROBOT(40, "dingDingRobot(钉钉机器人)","ding_ding_robot"),
    ;

    /**
     * 编码值
     */
    private Integer code;

    /**
     * 描述
     */
    private String description;

    /**
     * 内容模型Class
     */
    //private Class contentModelClass;

    /**
     * 英文标识
     */
    private String codeEn;

    /**
     * 通过code获取class
     * @param code
     * @return
     */
/*
    public static Class getChanelModelClassByCode(Integer code) {
        ChannelType[] values = values();
        for (ChannelType value : values) {
            if (value.getCode().equals(code)) {
                return value.getContentModelClass();
            }
        }
        return null;
    }
*/

    /**
     * 通过code获取enum
     * @param code
     * @return
     */
    public static ChannelType getEnumByCode(Integer code) {
        ChannelType[] values = values();
        for (ChannelType value : values) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        return null;
    }
}
