package com.example.message.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.example.message.common.model.TaskInfo;
import com.example.message.entity.MessageTemplate;
import com.example.message.entity.User;
import com.example.message.utils.KafkaUtils;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.security.auth.callback.Callback;
import java.util.List;

@Service
public class SendService {

    @Autowired
    private KafkaUtils kafkaUtils;

    @Autowired
    private MessageTemplateService templateService;

    @Autowired
    private UserService userService;

    @Value("${austin.business.topic.name}")
    private String topicName;

    public synchronized void process(Integer messageTemplateId){
        MessageTemplate messageTemplate=templateService.Get(messageTemplateId);
        List<User> users=userService.GetUserByMsgId(messageTemplateId);
        TaskInfo taskInfo=TaskInfo.builder().messageTemplateId(messageTemplateId)
                .receiver(users)
                .sendChannel(messageTemplate.getSendChannel())
                .content(messageTemplate.getMsgContent())
                .title(messageTemplate.getName())
                .build();
        String message = JSON.toJSONString(taskInfo, new SerializerFeature[]{SerializerFeature.WriteClassName});
        kafkaUtils.send(topicName,message);

    }
}
