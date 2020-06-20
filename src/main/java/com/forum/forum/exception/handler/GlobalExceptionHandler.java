package com.forum.forum.exception.handler;

import com.forum.forum.exception.CustomException;
import com.forum.forum.response.Result;
import com.forum.forum.response.ResultCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/**
 * @author ：Zack
 * @date ：Created in 2020/3/30 11:22
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(CustomException.class)
    public ResponseEntity handleException(CustomException e) {
        e.printStackTrace();
        if (e.getResultCode().code() == 0) {
            return new ResponseEntity(new Result(e.getResultCode(), e.getData()), HttpStatus.OK);
        } else if (e.getResultCode().code() > 70000) {
            return new ResponseEntity(new Result(e.getResultCode(), e.getData()), HttpStatus.UNAUTHORIZED);
        } else if (e.getResultCode().code() > 0) {
            return new ResponseEntity(new Result(e.getResultCode(), e.getData()), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity(new Result(e.getResultCode(), e.getData()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class, BindException.class})
    public Result handleException(Exception e) {
        e.printStackTrace();
        if (e instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException validException = (MethodArgumentNotValidException) e;
            BindingResult result = validException.getBindingResult();
            StringBuffer errorMsg = new StringBuffer();
            if (result.hasErrors()) {
                List<ObjectError> errors = result.getAllErrors();
                errors.forEach(p -> {
                    FieldError fieldError = (FieldError) p;
                    errorMsg.append(fieldError.getDefaultMessage()).append(",");
                    log.error("### 请求参数错误：{" + fieldError.getObjectName() + "},field{" + fieldError.getField() + "},errorMessage{" + fieldError.getDefaultMessage() + "}");
                });
            }
        } else if (e instanceof BindException) {
            BindException bindException = (BindException) e;
            if (bindException.hasErrors()) {
                log.error("### 请求参数错误: {}", bindException.getAllErrors());
            }
        }

        return new Result(ResultCode.PARAM_IS_INVALID);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity handleOtherException(Exception e) {
        e.printStackTrace();
        return new ResponseEntity(new Result(ResultCode.SYSTEM_INNER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
