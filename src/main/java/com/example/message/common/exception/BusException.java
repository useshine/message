package com.example.message.common.exception;

public class BusException extends RuntimeException{
    private static final long serialVersionUID=1L;
    /**
     * 错误码
     * TODO:定义不同的错误码
     */
    private Integer code = -1;

    /**
     * 错误提示
     */
    private String message;


    /**
     * 空构造方法，避免反序列化问题
     */
    public BusException()
    {
    }

    public BusException(String message)
    {
        this.message = message;
    }

    public BusException(Integer code,String message)
    {
        this.message = message;
        this.code = code;
    }


    public String getMessage()
    {
        return message;
    }

    public Integer getCode()
    {
        return code;
    }

    public BusException setMessage(String message)
    {
        this.message = message;
        return this;
    }
}
