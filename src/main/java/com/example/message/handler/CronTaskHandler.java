package com.example.message.handler;

import com.example.message.service.MessageTemplateService;
import com.example.message.service.SendService;
import com.example.message.utils.KafkaUtils;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


@Component
@Service
public class CronTaskHandler {

    @Autowired
    private SendService sendService;

    @Autowired
    private KafkaUtils kafkaUtils;

    @Autowired
    private MessageTemplateService templateService;

    @Value("${austin.business.topic.name}")
    private String topicName;

    @XxlJob("jobHandler")
    public void execute() throws Exception{
        System.out.println("發生消息");
        Integer messageTemplateId=Integer.valueOf(XxlJobHelper.getJobParam());
        sendService.process(messageTemplateId);
    }
}
