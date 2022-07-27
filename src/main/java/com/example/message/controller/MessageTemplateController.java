package com.example.message.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.message.entity.MessageTemplate;
import com.example.message.common.model.BasicResult;
import com.example.message.common.model.PageModel;
import com.example.message.service.MailService;
import com.example.message.vo.EditMessgeVo;
import com.example.message.vo.MessageTemplateVo;
import com.example.message.service.MessageTemplateService;
import com.example.message.vo.SearchVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "消息模板模块")
@RestController
@RequestMapping("/messageTemplate")
public class MessageTemplateController {

    @Autowired
    private MessageTemplateService messageTemplateService;

    @Autowired
    private MailService mailService;

    @ApiOperation("添加消息模板")
    @PostMapping("/Add")
    public BasicResult Add(@RequestBody MessageTemplateVo messageTemplateVo){
        MessageTemplate messageTemplate= messageTemplateService.AddMessage(messageTemplateVo);
        return  BasicResult.success(messageTemplate);
    }

    @ApiOperation("修改消息模板")
    @PostMapping("/Edit")
    public BasicResult Edit(@RequestBody EditMessgeVo editMessgeVo){
        messageTemplateService.UpdateMsg(editMessgeVo);
        MessageTemplate messageTemplate=messageTemplateService.Get(editMessgeVo.id);
        return BasicResult.success(messageTemplate);
    }

    @ApiOperation("/启动模板定时任务")
    @PostMapping("/start/{id}")
    public BasicResult start(@PathVariable("id") Integer id){
        return messageTemplateService.startCronTask(id);
    }

    @ApiOperation("暂停模板定时任务")
    @PostMapping("/stop/{id}")
    public BasicResult stop(@RequestBody @PathVariable("id") Integer id){
        return messageTemplateService.stopCronTask(id);
    }

    @ApiOperation("删除消息任务")
    @PostMapping("/delete/{id}")
    public BasicResult deleteById(@PathVariable("id") Integer id){
         messageTemplateService.deleteById(id);
         return BasicResult.success("删除成功");
    }

    @ApiOperation("条件查询")
    @PostMapping("/Search")
    public BasicResult<PageModel<MessageTemplate>> Search(@RequestBody SearchVo searchVo){
        Page<MessageTemplate> page=new Page<>();
        page.setCurrent(searchVo.getPageIndex());
        page.setSize(searchVo.getPageSize());
        IPage<MessageTemplate> messageTemplateIPage=messageTemplateService.Search(page,searchVo);
        PageModel<MessageTemplate> mPageModel=PageModel.transform(messageTemplateIPage);
        return BasicResult.success(mPageModel);
    }


}
