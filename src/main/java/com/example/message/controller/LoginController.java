package com.example.message.controller;

import com.example.message.common.model.BasicResult;
import com.example.message.vo.LoginVo;
import com.example.message.service.AccountService;
import com.example.message.service.TokenService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "登录")
@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private TokenService tokenService;

    @ApiOperation("登录")
    @PostMapping("")
    public BasicResult Login(@RequestBody LoginVo loginVo){
        return  BasicResult.success(accountService.Login(loginVo.username, loginVo.password));
    }
}
