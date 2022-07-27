package com.example.message.controller;

import com.example.message.common.exception.BusException;
import com.example.message.common.model.BasicResult;
import com.example.message.service.StorageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@Api("上传管理")
@RestController
@RequestMapping("/file")
public class StorageController {
    @Autowired
    private StorageService storageService;

    @PostMapping("/upload")
    @ApiOperation(value = "上传文件")
    public BasicResult handleFileUpload(HttpServletRequest request, @RequestPart("file") MultipartFile file) throws Exception {
        if(null == file) throw new BusException("上传空文件");
        String storePath = storageService.store(file);
        String downPath="http://"+request.getServerName()+":"+request.getServerPort()+"/download/"+storePath;
        return BasicResult.success(downPath);
    }
}
