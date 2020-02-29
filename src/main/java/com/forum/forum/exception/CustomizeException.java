package com.forum.forum.exception;

/**
 * @author ：Zack
 * @date ：Created in 2020/2/29 11:12
 */
public class CustomizeException extends RuntimeException{
    private String message;

    public CustomizeException(ICustomizeErrorCode errorCode) {
        this.message = errorCode.getMessage();
    }

    @Override
    public String getMessage() {
        return message;
    }

}
