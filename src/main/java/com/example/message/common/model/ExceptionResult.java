package com.example.message.common.model;

import java.util.HashMap;

public class ExceptionResult extends HashMap<String,Object> {
    private static final long serialVersionUID = 1L;

    /** 状态码 */
    public static final String CODE_TAG = "code";

    /** 返回内容 */
    public static final String MSG_TAG = "message";


    /**
     * 初始化一个新创建的 ExceptionResult 对象，使其表示一个空消息。
     */
    public ExceptionResult()
    {
    }

    /**
     * 初始化一个新创建的 ExceptionResult 对象
     *
     * @param code 状态码
     * @param msg 返回内容
     */
    public ExceptionResult(int code, String msg)
    {
        super.put(CODE_TAG, code);
        super.put(MSG_TAG, msg);
    }


    /**
     * 方便链式调用
     *
     * @param key
     * @param value
     * @return
     */
    @Override
    public ExceptionResult put(String key, Object value)
    {
        super.put(key, value);
        return this;
    }

    /**
     * 返回错误消息
     *
     * @return
     */
    public static ExceptionResult error()
    {
        return ExceptionResult.error("操作失败");
    }

    /**
     * 返回错误消息
     *
     * @param msg 返回内容
     * @return 警告消息
     */
    public static ExceptionResult error(String msg)
    {
        return ExceptionResult.error(-1, msg);
    }


    /**
     * 返回错误消息
     *
     * @param code 状态码
     * @param msg 返回内容
     * @return 警告消息
     */
    public static ExceptionResult error(int code, String msg)
    {
        return new ExceptionResult(code, msg);
    }
}
