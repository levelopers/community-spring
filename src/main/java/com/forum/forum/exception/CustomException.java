package com.forum.forum.exception;


import com.forum.forum.response.ResultCode;

/**
 * @author ：Zack
 * @date ：Created in 2020/3/30 11:22
 */
public class CustomException extends RuntimeException {

    private Object data;
    private ResultCode resultCode;

    public CustomException(ResultCode resultCode){
        super(resultCode.message());
        this.resultCode = resultCode;
    }

    public CustomException(ResultCode resultCode, Object data){
        super(resultCode.message());
        this.resultCode = resultCode;
        this.data = data;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public ResultCode getResultCode() {
        return resultCode;
    }

    public void setResultCode(ResultCode resultCode) {
        this.resultCode = resultCode;
    }
}
