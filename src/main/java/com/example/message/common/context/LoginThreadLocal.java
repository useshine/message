package com.example.message.common.context;


import com.example.message.dto.LoginDto;

public class LoginThreadLocal {
    private static ThreadLocal<LoginDto> loginThread =
            new ThreadLocal<>();

    public static void set(LoginDto loginDto){
        loginThread.set(loginDto);
    }

    public static LoginDto get(){
        return loginThread.get();
    }

    //防止内存泄漏
    public static void remove(){
        loginThread.remove();
    }

}
