package com.example.message.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.message.common.constants.MessageStatus;
import com.example.message.common.constants.RespStatusEnum;
import com.example.message.common.context.LoginThreadLocal;
import com.example.message.entity.MsgUser;
import com.example.message.mapper.MsgUserMapper;
import com.example.message.common.model.BasicResult;
import com.example.message.common.model.Xxl.XxlJobInfo;
import com.example.message.utils.XxlJobUtils;
import com.example.message.vo.EditMessgeVo;
import com.example.message.vo.MessageTemplateVo;
import com.example.message.mapper.MessageTemplateMapper;
import com.example.message.entity.MessageTemplate;
import com.example.message.vo.SearchVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class MessageTemplateService extends IService<MessageTemplate> {


    @Autowired
    private CronTaskService cronTaskService;

    @Autowired
    private XxlJobUtils xxlJobUtils;

    @Autowired
    private MessageTemplateMapper messageTemplateMapper;

    @Autowired
    private MsgUserMapper msgUserMapper;

    public MessageTemplate  AddMessage(MessageTemplateVo messageTemplateVo){
        String fromUser= LoginThreadLocal.get().getUsername();
        MessageTemplate messageTemplate=MessageTemplate.builder()
                .name(messageTemplateVo.name).msgStatus(10)
                .msgStatus(MessageStatus.INIT.getCode())
                .msgContent(messageTemplateVo.msgContent)
                .sendChannel(messageTemplateVo.sendChannel)
                .expectPushTime(messageTemplateVo.expectPushTime)
                .creator(fromUser)
                .createTime(Math.toIntExact(new Date().getTime()/ 1000L)).build();
        messageTemplateMapper.Add(messageTemplate);
        // 添加发送人员
        for(Integer userId :messageTemplateVo.userId){
            MsgUser msgUser=MsgUser.builder().msgId(messageTemplate.getId()).userId(userId)
                    .createTime(Math.toIntExact(new Date().getTime()/ 1000L)).build();
            AddMsgUser(msgUser);
        }
        return messageTemplate;
    }

    public BasicResult startCronTask(Integer id){
        String fromUser= LoginThreadLocal.get().getUsername();
        MessageTemplate messageTemplate=messageTemplateMapper.Get(id);
        XxlJobInfo xxlJobInfo=xxlJobUtils.buildXxlJobInfo(messageTemplate);

        Integer taskId=messageTemplate.getCronTaskId();
        BasicResult basicResult=cronTaskService.saveCronTask(xxlJobInfo);
        if(taskId==null&& RespStatusEnum.SUCCESS.getCode().equals(basicResult.getStatus())&& basicResult.getData()!=null){
            taskId=Integer.valueOf(String.valueOf(basicResult.getData()));
        }
        if(taskId!=null){
            cronTaskService.startCronTask(taskId);
            MessageTemplate clone= ObjectUtil.clone(messageTemplate).setCronTaskId(taskId).setMsgStatus(MessageStatus.RUN.getCode()).setUpdator(fromUser).setUpdateTime(Math.toIntExact(DateUtil.currentSeconds()));
            /*更新*/
            messageTemplateMapper.Save(clone);
            return BasicResult.success();
        }
        return BasicResult.fail();
    }

    public BasicResult stopCronTask(Integer id){
        String fromUser= LoginThreadLocal.get().getUsername();
        MessageTemplate messageTemplate=messageTemplateMapper.Get(id);
        MessageTemplate clone = ObjectUtil.clone(messageTemplate).setMsgStatus(MessageStatus.STOP.getCode()).setUpdator(fromUser).setUpdateTime(Math.toIntExact(DateUtil.currentSeconds()));
        messageTemplateMapper.Save(clone);

        // 暂停定时任务
        return cronTaskService.stopCronTask(clone.getCronTaskId());
    }

    public void deleteById(Integer id){
        MessageTemplate messageTemplate=messageTemplateMapper.Get(id);
        cronTaskService.deleteCronTask(messageTemplate.getCronTaskId());
        messageTemplateMapper.Delete(id);
    }

    public void AddMsgUser(MsgUser msgUser){
        msgUserMapper.Add(msgUser);
    }

    public IPage<MessageTemplate> Search(Page<MessageTemplate> page, SearchVo searchVo){
        return messageTemplateMapper.Search(page,searchVo);
    }

    public void UpdateMsg(EditMessgeVo editMessgeVo){
         messageTemplateMapper.UpdateMsg(editMessgeVo);
    }

}
