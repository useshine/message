package com.example.message.service;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.http.HttpResponse;
import com.alibaba.fastjson.JSON;
import com.example.message.common.constants.RespStatusEnum;
import com.example.message.common.constants.XxlJobConstant;
import com.example.message.common.model.BasicResult;
import com.example.message.common.model.Xxl.XxlJobGroup;
import com.example.message.common.model.Xxl.XxlJobInfo;
import com.xxl.job.core.biz.model.ReturnT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import cn.hutool.http.HttpRequest;
import org.springframework.stereotype.Service;

import java.net.HttpCookie;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class CronTaskService {
    @Value("${xxl.job.admin.username}")
    private String xxlUserName;

    @Value("${xxl.job.admin.password}")
    private String xxlPassword;

    @Value("${xxl.job.admin.address}")
    private String xxlAddresses;

    public BasicResult saveCronTask(XxlJobInfo xxlJobInfo) {
        Map<String, Object> params = JSON.parseObject(JSON.toJSONString(xxlJobInfo), Map.class);
        String path = xxlJobInfo.getId() == null ? xxlAddresses + XxlJobConstant.INSERT_URL :
                xxlAddresses + XxlJobConstant.UPDATE_URL;
        HttpResponse response;
        ReturnT returnT = null;
        try {
            response = HttpRequest.post(path).form(params).cookie(getCookie()).execute();
            returnT=JSON.parseObject(response.body(),ReturnT.class);
            if(response.isOk()&&ReturnT.SUCCESS_CODE==returnT.getCode()) {
                return BasicResult.success(returnT.getContent());
            }
        }catch (Exception e) {
            log.error("CronTaskService#startCronTask fail,e:{},param:{},response:{}", e.getMessage()
                    , JSON.toJSONString(params), JSON.toJSONString(returnT));
        }
        return BasicResult.fail(RespStatusEnum.SERVICE_ERROR,JSON.toJSONString(returnT));
    }

    /**
     * 获取xxl cookie
     *
     * @return String
     */
    private String getCookie() {
        Map<String, Object> params = MapUtil.newHashMap();
        params.put("userName", xxlUserName);
        params.put("password", xxlPassword);
        params.put("randomCode", IdUtil.fastSimpleUUID());

        String path = xxlAddresses + XxlJobConstant.LOGIN_URL;
        cn.hutool.http.HttpResponse response = null;
        try {
            response = HttpRequest.post(path).form(params).execute();
            if (response.isOk()) {
                List<HttpCookie> cookies = response.getCookies();
                StringBuilder sb = new StringBuilder();
                for (HttpCookie cookie : cookies) {
                    sb.append(cookie.toString());
                }
                return sb.toString();
            }
        } catch (Exception e) {
            log.error("CronTaskService#createGroup getCookie,e:{},param:{},response:{}", e.getMessage()
                    , JSON.toJSONString(params), JSON.toJSONString(response));
        }
        return null;
    }

    public BasicResult startCronTask(Integer taskId){
        String path=xxlAddresses+XxlJobConstant.RUN_URL;
        HashMap<String,Object> params=MapUtil.newHashMap();
        params.put("id",taskId);

        HttpResponse response;
        ReturnT returnT=null;
        try {
            response= HttpRequest.post(path).form(params).cookie(getCookie()).execute();
            returnT=JSON.parseObject(response.body(),ReturnT.class);
            if(response.isOk()&&ReturnT.SUCCESS_CODE==returnT.getCode()){
                return BasicResult.success();
            }
        }catch (Exception e){
            log.error("CronTaskService#startCronTask fail,e:{},param:{},response:{}", e.getMessage()
                    , JSON.toJSONString(params), JSON.toJSONString(returnT));
        }
        return BasicResult.fail(RespStatusEnum.SERVICE_ERROR,JSON.toJSONString(returnT));
    }

    public BasicResult stopCronTask(Integer taskId){
        String path=xxlAddresses+XxlJobConstant.STOP_URL;
        HashMap<String, Object> params = MapUtil.newHashMap();
        params.put("id", taskId);

        HttpResponse response;
        ReturnT returnT = null;
        try {
            response = HttpRequest.post(path).form(params).cookie(getCookie()).execute();
            returnT = JSON.parseObject(response.body(), ReturnT.class);
            if (response.isOk() && ReturnT.SUCCESS_CODE == returnT.getCode()) {
                return BasicResult.success();
            }
        } catch (Exception e) {
            log.error("CronTaskService#stopCronTask fail,e:{},param:{},response:{}", e.getMessage()
                    , JSON.toJSONString(params), JSON.toJSONString(returnT));
        }
        return BasicResult.fail(RespStatusEnum.SERVICE_ERROR, JSON.toJSONString(returnT));
    }

    public BasicResult deleteCronTask(Integer taskId){
        String path = xxlAddresses + XxlJobConstant.DELETE_URL;

        HashMap<String, Object> params = MapUtil.newHashMap();
        params.put("id", taskId);

        HttpResponse response;
        ReturnT returnT = null;
        try {
            response = HttpRequest.post(path).form(params).cookie(getCookie()).execute();
            returnT = JSON.parseObject(response.body(), ReturnT.class);
            if (response.isOk() && ReturnT.SUCCESS_CODE == returnT.getCode()) {
                return BasicResult.success();
            }
        } catch (Exception e) {
            log.error("CronTaskService#deleteCronTask fail,e:{},param:{},response:{}",e.getMessage()
                    , JSON.toJSONString(params), JSON.toJSONString(returnT));
        }
        return BasicResult.fail(RespStatusEnum.SERVICE_ERROR, JSON.toJSONString(returnT));
    }


    public BasicResult getGroupId(String appName,String title){
        String path=xxlAddresses+XxlJobConstant.JOB_GROUP_PAGE_LIST;
        HashMap<String, Object> params = MapUtil.newHashMap();
        params.put("appname", appName);
        params.put("title", title);

        HttpResponse response = null;
        try {
            response = HttpRequest.post(path).form(params).cookie(getCookie()).execute();
            Integer id = JSON.parseObject(response.body()).getJSONArray("data").getJSONObject(0).getInteger("id");
            if (response.isOk() && id != null) {
                return BasicResult.success(id);
            }
        } catch (Exception e) {
            log.error("CronTaskService#getGroupId fail,e:{},param:{},response:{}", e.getMessage()
                    , JSON.toJSONString(params), JSON.toJSONString(response.body()));
        }
        return BasicResult.fail(RespStatusEnum.SERVICE_ERROR, JSON.toJSONString(response.body()));
    }

    public BasicResult createGroup(XxlJobGroup xxlJobGroup){
        Map<String, Object> params = JSON.parseObject(JSON.toJSONString(xxlJobGroup), Map.class);
        String path = xxlAddresses + XxlJobConstant.JOB_GROUP_INSERT_URL;

        HttpResponse response;
        ReturnT returnT = null;

        try {
            response = HttpRequest.post(path).form(params).cookie(getCookie()).execute();
            returnT = JSON.parseObject(response.body(), ReturnT.class);
            if (response.isOk() && ReturnT.SUCCESS_CODE == returnT.getCode()) {
                return BasicResult.success();
            }
        } catch (Exception e) {
            log.error("CronTaskService#createGroup fail,e:{},param:{},response:{}", e.getMessage()
                    , JSON.toJSONString(params), JSON.toJSONString(returnT));
        }
        return BasicResult.fail(RespStatusEnum.SERVICE_ERROR, JSON.toJSONString(returnT));
    }
}
