package com.example.message.controller;

import com.example.message.common.model.BasicResult;
import com.example.message.service.SendService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "发送消息模块")
@RestController
@RequestMapping("/sendMsg")
public class SendController {

    @Autowired
    private SendService sendService;

    @ApiOperation("发送及时消息")
    @GetMapping("/Send/{id}")
    public BasicResult Send(@PathVariable Integer id){
        sendService.process(id);
        return BasicResult.success("发送成功");
    }
}
