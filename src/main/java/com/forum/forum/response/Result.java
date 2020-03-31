package com.forum.forum.response;

import lombok.Data;

/**
 * @author ：Zack
 * @date ：Created in 2020/3/22 23:31
 */
@Data
public class Result<T> {
    private int code;
    private String message;
    private T data;

    public Result(ResultCode resultCode) {
        this.code = resultCode.code();
        this.message = resultCode.message();
    }

    public Result(ResultCode resultCode, T data) {
        this.code = resultCode.code();
        this.message = resultCode.message();
        this.data = data;
    }

    public Result(String message) {
        this.message = message;
    }

    public static Result okOf() {
        return new Result(ResultCode.SUCCESS);
    }

    public static <T> Result okOf(T data) {
        return new Result(ResultCode.SUCCESS, data);
    }

    public static <T> Result okOf(ResultCode resultCode, T data) {
        return new Result(resultCode, data);
    }

    public static Result errorOf() {
        return new Result(ResultCode.FAIL);
    }

    public static Result errorOf(ResultCode resultCode) {
        return new Result(resultCode);
    }


}
