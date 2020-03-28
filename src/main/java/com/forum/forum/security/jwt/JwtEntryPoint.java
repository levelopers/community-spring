package com.forum.forum.security.jwt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author ：Zack
 * @date ：Created in 2020/3/22 23:31
 */
@Component
public class JwtEntryPoint implements AuthenticationEntryPoint {


    private static final Logger logger = LoggerFactory.getLogger(JwtEntryPoint.class);

    // called if authentication failed
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException e){

        logger.error("Unauthorized error. Message - {}", e.getMessage());
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
    }
}
