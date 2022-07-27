package com.example.message.service;

import com.example.message.common.exception.BusException;
import com.example.message.entity.User;
import com.example.message.mapper.UserMapper;
import com.example.message.utils.StringUtils;
import io.swagger.models.auth.In;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.apache.http.entity.ContentType;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserService extends IService<User> {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private StorageService storageService;

    public List<User> GetUserByMsgId(Integer id){
        return userMapper.GetUserByMsgId(id);
    }

    public List<User> GetAll(){return userMapper.GetAll();}

    public Integer AddUserByExcel(String filePath)throws Exception{
        String[] split=filePath.split("/");
        String fileName = split[split.length - 1];
        String dirName = split[split.length - 2];
        Path load = storageService.load(dirName + "/" + fileName);
        File file = new File(String.valueOf(load));
        FileInputStream fileInputStream = new FileInputStream(file);
        MultipartFile userFile = new MockMultipartFile(file.getName(), file.getName(),
                ContentType.APPLICATION_OCTET_STREAM.toString(), fileInputStream);

        List<User> users=new ArrayList<>();
        //判断文件版本
        //HSSFWorkbook:是操作Excel2003以前（包括2003）的版本，扩展名是.xls；
        //XSSFWorkbook:是操作Excel2007后的版本，扩展名是.xlsx；
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
        InputStream inputStream = userFile.getInputStream();
        Workbook wb;
        if (suffix.equals("xlsx")) {
            wb = new XSSFWorkbook(inputStream);
        } else if (suffix.equals("xls")) {
            wb = new HSSFWorkbook(inputStream);
        } else {
            throw new BusException("请上传excel文件！");
        }
        //获取excel表单
        //读取第一个工作表(这里的下标与list一样的，从0开始取，之后的也是如此)
        Sheet sheet = wb.getSheetAt(0);

        //读入第一行的名称
        List<String> importName = new ArrayList<>();
        Row firstRow = sheet.getRow(0);
        int lastcolumnNum = firstRow.getLastCellNum();
        int column = 0;
        while (column < lastcolumnNum) {
            firstRow.getCell(column).setCellType(CellType.STRING);
            //拿到第一行要导入的名称
            String name = firstRow.getCell(column).getStringCellValue();
            importName.add(name);
            column++;
        }
        Integer failCount = 0;

        if(null!=sheet){
            for (int line = 1; line <= sheet.getLastRowNum(); line++) {
                User user=new User();
                //获取第line行的数据
                Row row = sheet.getRow(line);
                if (null == row) {
                    continue;
                }
                String name=null;
                String telephone=null;
                String email=null;
                String dingToken=null;
                String dingSign=null;
                String wechatSign=null;
                Integer createTime=Math.toIntExact(new Date().getTime()/ 1000L);
                boolean flag=false;
                for (int colNum = 0; colNum < lastcolumnNum; colNum++) {

                    if (importName.get(colNum).equals("name")) {
                        Cell cell = row.getCell(colNum);
                        //判断该单元格是否为空
                        if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK || StringUtils.isEmpty((cell + "").trim())) {
                            return null;
                        } else {
                            row.getCell(colNum).setCellType(CellType.STRING);
                            name = row.getCell(colNum).getStringCellValue();
                            User user1 =userMapper.GetByName(name);
                            if(user1!=null){
                                return  -1;
                            }
                        }
                        continue;
                    }
                    if (importName.get(colNum).equals("telephone")) {
                        Cell cell = row.getCell(colNum);
                        //判断该单元格是否为空
                        if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK || StringUtils.isEmpty((cell + "").trim())) {
                            continue;
                        } else {
                            row.getCell(colNum).setCellType(CellType.STRING);
                            telephone = row.getCell(colNum).getStringCellValue();
                        }
                        continue;
                    }
                    if (importName.get(colNum).equals("email")) {
                        Cell cell = row.getCell(colNum);
                        //判断该单元格是否为空
                        if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK || StringUtils.isEmpty((cell + "").trim())) {
                            continue;
                        } else {
                            row.getCell(colNum).setCellType(CellType.STRING);
                            email = row.getCell(colNum).getStringCellValue();
                        }
                        continue;
                    }
                    if (importName.get(colNum).equals("dingToken")) {
                        Cell cell = row.getCell(colNum);
                        //判断该单元格是否为空
                        if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK || StringUtils.isEmpty((cell + "").trim())) {
                            continue;
                        } else {
                            row.getCell(colNum).setCellType(CellType.STRING);
                            dingToken = row.getCell(colNum).getStringCellValue();
                        }
                        continue;
                    }
                    if (importName.get(colNum).equals("dingSign")) {
                        Cell cell = row.getCell(colNum);
                        //判断该单元格是否为空
                        if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK || StringUtils.isEmpty((cell + "").trim())) {
                            continue;
                        } else {
                            row.getCell(colNum).setCellType(CellType.STRING);
                            dingSign = row.getCell(colNum).getStringCellValue();
                        }
                        continue;
                    }
                    if (importName.get(colNum).equals("wechatSign")) {
                        Cell cell = row.getCell(colNum);
                        //判断该单元格是否为空
                        if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK || StringUtils.isEmpty((cell + "").trim())) {
                            continue;
                        } else {
                            row.getCell(colNum).setCellType(CellType.STRING);
                            wechatSign = row.getCell(colNum).getStringCellValue();
                        }
                        continue;
                    }
                }
                if(flag){
                    continue;
                }
                user.setCreateTime(createTime)
                        .setName(name)
                        .setTelephone(telephone)
                        .setEmail(email)
                        .setDingSign(dingSign)
                        .setDingToken(dingToken)
                        .setWeChatSign(wechatSign);
                users.add(user);
            }
            for(User user:users){
                userMapper.Add(user);
            }
        }
        return 0;
    }


}
