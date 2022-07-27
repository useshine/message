package com.example.message.common.constants;

public class WeChatConstant {

    public static String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";

    public static String SEND_URL = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";

    public static String appId="wx9471fedd84815325";

    public static String appsecret="f380269b2784f8d7d94c26f190b2fbbe";

    public static class AccessToken{
        public static String token=null;
        public static Long expiration=0L;
    }
}
