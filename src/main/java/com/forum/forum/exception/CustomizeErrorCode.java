package com.forum.forum.exception;

/**
 * @author ：Zack
 * @date ：Created in 2020/2/29 11:45
 */
public enum CustomizeErrorCode implements ICustomizeErrorCode{
    QUESTION_NOT_FOUND("你找到问题不在了，要不要换个试试？");

    private String message;

    CustomizeErrorCode(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
