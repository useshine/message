package com.example.message.controller;

import com.example.message.common.exception.BusException;
import com.example.message.entity.User;
import com.example.message.common.model.BasicResult;
import com.example.message.service.UserService;
import com.example.message.vo.FileVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api("用户管理")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;


    @ApiOperation("添加用户")
    @PostMapping("/Add")
    public BasicResult Add(@RequestBody User user){
        userService.Add(user);
        return  BasicResult.success("添加成功");
    }

    @ApiOperation("导入excel新增终端信息")
    @PostMapping("/AddUserByExcel")
    public BasicResult AddFile(@RequestBody FileVo fileVo) throws Exception{
        Integer res=userService.AddUserByExcel(fileVo.getFilePath());
        if(res==-1){
            throw new BusException("导入用户信息中存在已有用户，请修改后重新导入");
        }else if(res==null){
            throw new BusException("导入用户信息不正确，’name‘为空");
        }
        return  BasicResult.success("添加成功");
    }

    @ApiOperation("删除用户")
    @PostMapping("/Delete")
    public BasicResult Delete(){
        return  BasicResult.success("添加成功");
    }

    @ApiOperation("查看用户详细信息")
    @GetMapping("/GetDetail/{id}")
    public BasicResult GetDetail(@PathVariable Integer id){
        return BasicResult.success(userService.Get(id));
    }

    @ApiOperation("获取所有用户")
    @GetMapping("/GetAll")
    public BasicResult GetAll(){
        return BasicResult.success(userService.GetAll());
    }

}
