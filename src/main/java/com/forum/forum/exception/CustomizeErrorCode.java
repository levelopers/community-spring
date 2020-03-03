package com.forum.forum.exception;

/**
 * @author ：Zack
 * @date ：Created in 2020/2/29 11:45
 */
public enum CustomizeErrorCode implements ICustomizeErrorCode{
    QUESTION_NOT_FOUND(2001,"question not found"),
    TAGET_PARENT_NOT_FOUNT(2002, "no question or comment selected"),
    NO_LOGIN(2003,"you need to login"),
    SYS_ERROR(2004, "system error"),
    TYPE_PARAM_WRONG(2005,"no type parameter in comment"),
    COMMENT_NOT_FOUND(2006,"comment not found"),
    CONTENT_IS_EMPTY(2007, "content is empty"),
    READ_NOTIFICATION_FAIL(2008, "兄弟你这是读别人的信息呢？"),
    NOTIFICATION_NOT_FOUND(2009, "消息莫非是不翼而飞了？"),
    FILE_UPLOAD_FAIL(2010, "图片上传失败"),
    INVALID_INPUT(2011, "非法输入"),
    INVALID_OPERATION(2012, "兄弟，是不是走错房间了？"),
    ;
    private Integer code;
    private String message;

    CustomizeErrorCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
