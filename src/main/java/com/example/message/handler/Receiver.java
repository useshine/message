package com.example.message.handler;

import com.alibaba.fastjson.JSON;
import com.example.message.common.model.TaskInfo;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class Receiver {

    @Autowired
    private ApplicationContext context;

    @Autowired
    private HandlerHolder handlerHolder;

    @KafkaListener(topics = "#{'${austin.business.topic.name}'}")
    public void consumer(ConsumerRecord<?, String> record){
        System.out.println("WsMsgConsumer==>{},kafka_record==>{}"+record.topic()+" : "+ record.toString());
        Optional<String> kafkaMessage = Optional.ofNullable(record.value());
        if (kafkaMessage.isPresent()) {
            TaskInfo taskInfo=JSON.parseObject(kafkaMessage.get(), TaskInfo.class);
            handlerHolder.route(taskInfo.getSendChannel()).doHandler(taskInfo);
        }
    }
}
