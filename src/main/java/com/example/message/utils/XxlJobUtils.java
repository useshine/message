package com.example.message.utils;

import cn.hutool.core.date.DateUtil;
import com.example.message.common.constants.AustinConstant;
import com.example.message.common.constants.RespStatusEnum;
import com.example.message.common.constants.XxlJobConstant;
import com.example.message.common.model.BasicResult;
import com.example.message.entity.MessageTemplate;
import com.example.message.common.model.Xxl.XxlJobGroup;
import com.example.message.common.model.Xxl.XxlJobInfo;
import com.example.message.service.CronTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class XxlJobUtils {

    @Value("${xxl.job.executor.appname}")
    private String appName;

    @Value("${xxl.job.executor.jobHandlerName}")
    private String jobHandlerName;

    @Autowired
    private CronTaskService cronTaskService;
    /*
    * 构建xxlJobInfo信息
    * */
    public XxlJobInfo buildXxlJobInfo(MessageTemplate messageTemplate){
        String scheduleConf=messageTemplate.getExpectPushTime();
        if(messageTemplate.getExpectPushTime().equals(String.valueOf(AustinConstant.FALSE))){
            scheduleConf= DateUtil.format(DateUtil.offsetSecond(new Date(), XxlJobConstant.DELAY_TIME),AustinConstant.CRON_FORMAT);
        }
        XxlJobInfo xxlJobInfo=XxlJobInfo.builder()
                .jobGroup(queryJobGroupId())
                .jobDesc(messageTemplate.getName())
                .author(messageTemplate.getCreator())
                .scheduleConf(scheduleConf)
                .scheduleType("CRON")
                .misfireStrategy("DO_NOTHING")
                .executorRouteStrategy("CONSISTENT_HASH")
                .executorHandler(XxlJobConstant.JOB_HANDLER_NAME)
                .executorParam(String.valueOf(messageTemplate.getId()))
                .executorBlockStrategy("SERIAL_EXECUTION")
                .executorTimeout(XxlJobConstant.TIME_OUT)
                .executorFailRetryCount(XxlJobConstant.RETRY_COUNT)
                .glueType("BEAN")
                .triggerStatus(0).build();
        if(messageTemplate.getCronTaskId()!=null){
            xxlJobInfo.setId(messageTemplate.getCronTaskId());
        }
        return xxlJobInfo;
    }

    private Integer queryJobGroupId(){
        BasicResult basicResult=cronTaskService.getGroupId(appName,jobHandlerName);
        if (basicResult.getData() == null) {
            XxlJobGroup xxlJobGroup = XxlJobGroup.builder().appname(appName).title(jobHandlerName).addressType(AustinConstant.FALSE).build();
            if (RespStatusEnum.SUCCESS.getCode().equals(cronTaskService.createGroup(xxlJobGroup).getStatus())) {
                return (int) cronTaskService.getGroupId(appName, jobHandlerName).getData();
            }
        }
        return (Integer) basicResult.getData();
    }


}
