package com.example.message.handler;

import com.example.message.common.constants.ChannelType;
import com.example.message.common.exception.BusException;
import com.example.message.entity.User;
import com.example.message.common.model.MailPojo;
import com.example.message.common.model.TaskInfo;
import com.example.message.service.UserService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.stream.Collectors;


@Component
public class EmailHandler extends BaseHandler implements  Handler{

    @Value("${spring.mail.username}")
    String fromUser;

    @Autowired
    private UserService userService;

    @Resource
    JavaMailSender javaMailSender;

    public EmailHandler(){
        channelCode = ChannelType.EMAIL.getCode();
    }

    @Override
    public boolean handler(TaskInfo taskInfo) {
        String[] receivers=new String[taskInfo.getReceiver().size()];
        taskInfo.getReceiver().stream().map(User::getEmail).collect(Collectors.toList()).toArray(receivers);
        MailPojo mailPojo= MailPojo.builder().title(taskInfo.getTitle()).sentTo(receivers).text(taskInfo.getContent()).build();
        try {
            sendSimpleMail(mailPojo);
        }catch (Exception e){
            throw new BusException(e.getMessage());
        }
        return  true;
    }

    private void sendSimpleMail(MailPojo mailPojo){
        SimpleMailMessage message=new SimpleMailMessage();
        message.setSubject(mailPojo.getTitle());
        message.setFrom(fromUser);
        message.setTo(mailPojo.getSentTo());
        message.setSentDate(new Date());
        message.setText(mailPojo.getText());
        javaMailSender.send(message);
    }

    @SneakyThrows
    public void sendMineMail(MailPojo mailPojo){
        MimeMessage mimeMessage=javaMailSender.createMimeMessage();
        /*??????????????????*/
        mimeMessage.setSentDate(new Date());
        MimeMessageHelper helper=new MimeMessageHelper(mimeMessage,true);
        helper.setSubject(mailPojo.getText());
        helper.setText(mailPojo.getText(),true);
        helper.setTo(mailPojo.getSentTo());
        helper.setFrom(fromUser);
        String[] fileIds=mailPojo.getFileIds();
        if(fileIds!=null){
            for(String fileId:fileIds){
                /*????????????????????????*/
            }
        }
    }

    @Override
    public boolean doHandler(TaskInfo taskInfo) {
        if(handler(taskInfo)){
            return true;
        }
        return false;
    }
}
