package com.example.message.service;

import com.example.message.common.model.MailPojo;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;
import java.util.Date;

@Service
public class MailService {
    @Value("${spring.mail.username}")
    String fromUser;

    @Resource
    JavaMailSender javaMailSender;

    public void sendSimpleMail(MailPojo mailPojo){
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
        /*设置发送日期*/
        mimeMessage.setSentDate(new Date());
        MimeMessageHelper helper=new MimeMessageHelper(mimeMessage,true);
        helper.setSubject(mailPojo.getText());
        helper.setText(mailPojo.getText(),true);
        helper.setTo(mailPojo.getSentTo());
        helper.setFrom(fromUser);
        String[] fileIds=mailPojo.getFileIds();
        if(fileIds!=null){
            for(String fileId:fileIds){
               /*设置发送文件信息*/
            }
        }
    }

}
