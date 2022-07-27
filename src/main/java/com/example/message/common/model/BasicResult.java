package com.example.message.common.model;

import com.example.message.common.constants.RespStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public final class BasicResult<T>{
    /**
     * 响应状态
     */
    private String status;

    /**
     * 响应编码
     */
    private String msg;

    /**
     * 返回数据
     */
    private T data;

    public BasicResult(RespStatusEnum status) {
        this(status, null);
    }

    public BasicResult(RespStatusEnum status, T data) {
        this(status, status.getMsg(), data);
    }

    public BasicResult(RespStatusEnum status, String msg, T data) {
        this.status = status.getCode();
        this.msg = msg;
        this.data = data;
    }

    /**
     * @return 默认成功响应
     */
    public static BasicResult<Void> success() {
        return new BasicResult<>(RespStatusEnum.SUCCESS);
    }

    /**
     * 自定义信息的成功响应
     * <p>通常用作插入成功等并显示具体操作通知如: return BasicResultVO.success("发送信息成功")</p>
     *
     * @param msg 信息
     * @return 自定义信息的成功响应
     */
    public static <T> BasicResult<T> success(String msg) {
        return new BasicResult<>(RespStatusEnum.SUCCESS, msg, null);
    }

    /**
     * 带数据的成功响应
     *
     * @param data 数据
     * @return 带数据的成功响应
     */
    public static <T> BasicResult<T> success(T data) {
        return new BasicResult<>(RespStatusEnum.SUCCESS, data);
    }

    /**
     * @return 默认失败响应
     */
    public static <T> BasicResult<T> fail() {
        return new BasicResult<>(
                RespStatusEnum.FAIL,
                RespStatusEnum.FAIL.getMsg(),
                null
        );
    }

    /**
     * 自定义错误信息的失败响应
     *
     * @param msg 错误信息
     * @return 自定义错误信息的失败响应
     */
    public static <T> BasicResult<T> fail(String msg) {
        return fail(RespStatusEnum.FAIL, msg);
    }

    /**
     * 自定义状态的失败响应
     *
     * @param status 状态
     * @return 自定义状态的失败响应
     */
    public static <T> BasicResult<T> fail(RespStatusEnum status) {
        return fail(status, status.getMsg());
    }

    /**
     * 自定义状态和信息的失败响应
     *
     * @param status 状态
     * @param msg    信息
     * @return 自定义状态和信息的失败响应
     */
    public static <T> BasicResult<T> fail(RespStatusEnum status, String msg) {
        return new BasicResult<>(status, msg, null);
    }

}
