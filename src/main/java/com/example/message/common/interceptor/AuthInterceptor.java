package com.example.message.common.interceptor;

import com.example.message.common.context.LoginThreadLocal;
import com.example.message.dto.LoginDto;
import com.example.message.common.exception.BusException;
import com.example.message.entity.Account;
import com.example.message.service.AccountService;
import com.example.message.service.TokenService;
import com.example.message.utils.SpringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.AsyncHandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AuthInterceptor implements AsyncHandlerInterceptor {


    public TokenService tokenService= SpringUtils.getBean(TokenService.class);
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String token=request.getHeader("token");
        if(null == token){
            token = request.getParameter("token");
        }
        if(null == token)
            throw new BusException(-2,"没发现token参数");
        Account account = tokenService.getAccountByToken(token);
        //b端
        if(account!=null){
            //判断token有效期
            if(!tokenService.isValid(account.getTokenValidPeriod())){
                throw new BusException(-2,"登录信息已过期，请重新登录");
            }
            LoginDto loginDto = new LoginDto(account);
            LoginThreadLocal.set(loginDto);
        }else{
            throw new BusException(-2,"平台用户token异常");
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        LoginThreadLocal.remove();
    }
}