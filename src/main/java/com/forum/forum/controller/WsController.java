package com.forum.forum.controller;

import com.forum.forum.dto.QuestionDTO;
import com.forum.forum.dto.WsResponseDTO;
import com.forum.forum.exception.CustomException;
import com.forum.forum.model.Notification;
import com.forum.forum.model.User;
import com.forum.forum.response.Result;
import com.forum.forum.response.ResultCode;
import com.forum.forum.service.NotificationService;
import com.forum.forum.service.QuestionService;
import com.forum.forum.service.UserService;
import com.forum.forum.service.WsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author ：Zack
 * @date ：Created in 2020/6/19 17:24
 */
@Controller
public class WsController {
    @Autowired
    private WsService wsService;

    @Autowired
    private UserService userService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private NotificationService notificationService;

    @PostMapping("/makeComment/{questionId}")
    @ResponseBody
    @Transactional
    public Result makeComment(@PathVariable(name = "questionId") Long questionId,
                              HttpServletRequest request) {
        QuestionDTO commentedQuestionDTO = questionService.findById(questionId);
        String receiver = commentedQuestionDTO.getUser().getUsername();
        User sender = userService.getCurrentUser(request);
        if (sender == null) {
            throw new CustomException(ResultCode.USER_NOT_LOGGED_IN, "request.headers.authentication");
        }
        String senderName = sender.getUsername();
        WsResponseDTO wsResponseDTO = new WsResponseDTO();
        wsResponseDTO.setMessage(senderName
                + " just make a comment to your question "
                + "\""
                + commentedQuestionDTO.getTitle()
                + "\""
        );
        wsResponseDTO.setSender(senderName);
        wsResponseDTO.setUrl("/questions/" + questionId);
        wsService.notify(
                wsResponseDTO,
                receiver
        );
        Notification notification = new Notification();
        notification.setGmtCreate(System.currentTimeMillis());
        notification.setMessage(wsResponseDTO.getMessage());
        notification.setReceiverId(commentedQuestionDTO.getUser().getId());
        notification.setSenderId(sender.getId());
        notification.setRedirectUri("/questions/" + questionId);
        notificationService.save(notification);
        return Result.okOf();
    }
}
