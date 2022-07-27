package com.example.message.handler;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiRobotSendRequest;
import com.dingtalk.api.response.OapiRobotSendResponse;
import com.example.message.common.constants.ChannelType;
import com.example.message.common.exception.BusException;
import com.example.message.entity.User;
import com.example.message.common.model.TaskInfo;
import com.example.message.service.UserService;
import com.taobao.api.ApiException;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DingDingRobootHandler extends BaseHandler implements Handler{

    @Autowired
    private UserService userService;

    public static DingTalkClient client;

    public DingDingRobootHandler(){
        channelCode= ChannelType.DING_DING_ROBOT.getCode();
    }

    @Override
    public boolean handler(TaskInfo taskInfo){
        try {
            User user = taskInfo.getReceiver().get(0);
            List<String> mobileList=taskInfo.getReceiver().stream().map(User::getTelephone).collect(Collectors.toList());
            init(user.getDingToken(), user.getDingSign());
            OapiRobotSendRequest.Text text = new OapiRobotSendRequest.Text();
            text.setContent(taskInfo.getContent());
            OapiRobotSendRequest request = new OapiRobotSendRequest();
            if(!CollectionUtils.isEmpty(taskInfo.getReceiver())){
                OapiRobotSendRequest.At at=new OapiRobotSendRequest.At();
                at.setAtMobiles(mobileList);
               // at.setIsAtAll(true);
                request.setAt(at);
            }
            request.setMsgtype("text");
            request.setText(text);
            OapiRobotSendResponse response = new OapiRobotSendResponse();
            try {
                response = DingDingRobootHandler.client.execute(request);
            } catch (ApiException e) {
                System.out.println(e.getMessage());
            }
        }catch (Exception e){
            throw new BusException(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean doHandler(TaskInfo taskInfo) {
        if(handler(taskInfo)){
            return true;
        }
        return false;
    }

    private void init(String token,String secret){
        try {
            Long timestamp=System.currentTimeMillis();
            String stringToSign=timestamp+"\n"+secret;
            Mac mac=Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(secret.getBytes("UTF-8"), "HmacSHA256"));
            byte[] signData = mac.doFinal(stringToSign.getBytes("UTF-8"));
            String sign = URLEncoder.encode(new String(Base64.encodeBase64(signData)),"UTF-8");
            String post="https://oapi.dingtalk.com/robot/send?access_token="+token+"&timestamp="+timestamp+"&sign="+sign;
            client=new DefaultDingTalkClient(post);
        }catch (Exception e){
            throw new BusException(e.getMessage());
        }
    }
}
