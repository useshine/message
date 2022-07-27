package com.example.message.common.exception;


import com.example.message.common.model.ExceptionResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(RuntimeException.class)
    public ExceptionResult handleRuntimeException(RuntimeException e){
        return ExceptionResult.error(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ExceptionResult handleException(Exception e){
        return  ExceptionResult.error(e.getMessage());
    }

    @ExceptionHandler(BusException.class)
    public ExceptionResult handleBusException(BusException e){
        if(null== e.getMessage()){
            return ExceptionResult.error(e.getCode(),"操作失败");
        }
        return ExceptionResult.error(e.getCode(),e.getMessage());
    }
}
