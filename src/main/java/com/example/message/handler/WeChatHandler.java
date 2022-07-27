package com.example.message.handler;

import cn.hutool.http.HttpStatus;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.message.common.constants.ChannelType;
import com.example.message.common.constants.WeChatConstant;
import com.example.message.common.exception.BusException;
import com.example.message.entity.User;
import com.example.message.common.model.TaskInfo;
import com.example.message.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component
public class WeChatHandler extends BaseHandler implements Handler {

    @Autowired
    private UserService userService;


    private RestTemplate restTemplate;

    public WeChatHandler(){
        channelCode = ChannelType.ENTERPRISE_WE_CHAT.getCode();
        restTemplate=new RestTemplate();
    }

    @Override
    public boolean handler(TaskInfo taskInfo) {
        String accessToken=getToken();
        String url=WeChatConstant.SEND_URL.replace("ACCESS_TOKEN",accessToken);
        for (User user :taskInfo.getReceiver()) {
            Map<String, Object> sendBody = new HashMap<>();
            sendBody.put("touser", user.getWeChatSign());
            sendBody.put("topcolor", "#FF0000");          // 顶色
            sendBody.put("data", taskInfo.getContent());
            sendBody.put("template_id", "PGXmgglZHqGgkW4p2V5z9_x7rnaPTfjEQG03Zu_DYHU");
            ResponseEntity<String> forEntity = restTemplate.postForEntity(url, sendBody, String.class);
            if (forEntity.getStatusCodeValue() == HttpStatus.HTTP_OK) {
                System.out.println("发送成功----------------");
            }
        }
        return true;
    }

    @Override
    public boolean doHandler(TaskInfo taskInfo) {
        if(handler(taskInfo)){
            return true;
        }
        return false;
    }

    public String getToken(){
        if(WeChatConstant.AccessToken.expiration<=System.currentTimeMillis()){
            String url=WeChatConstant.ACCESS_TOKEN_URL
                    .replace("APPID",WeChatConstant.appId)
                    .replace("APPSECRET",WeChatConstant.appsecret);
            ResponseEntity<String> forEntity=restTemplate.getForEntity(url,String.class);
            JSONObject jsonObject= JSON.parseObject(forEntity.getBody());
            Object errcode=jsonObject.get("errcode");
            if(errcode!=null&&"40013".equals(errcode.toString())){
                throw new BusException("token异常");
            }
            WeChatConstant.AccessToken.token=jsonObject.get("access_token").toString();
            WeChatConstant.AccessToken.expiration=((Integer.parseInt(jsonObject.get("expires_in").toString())-1)*1000)+ System.currentTimeMillis();
        }
        return WeChatConstant.AccessToken.token;
    }
}
