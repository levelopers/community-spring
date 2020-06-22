package com.forum.forum.controller;

import com.forum.forum.dto.QuestionDTO;
import com.forum.forum.dto.WsResponseDTO;
import com.forum.forum.model.User;
import com.forum.forum.response.Result;
import com.forum.forum.service.QuestionService;
import com.forum.forum.service.UserService;
import com.forum.forum.service.WsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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

    @PostMapping("/makeComment/{questionId}")
    @ResponseBody
    public Result makeComment(@PathVariable(name = "questionId") Long questionId,
                              HttpServletRequest request) {
        QuestionDTO commentedQuestionDTO = questionService.findById(questionId);
        String receiver = commentedQuestionDTO.getUser().getUsername();

        User sender = userService.getCurrentUser(request);
        String senderName = sender.getUsername();
        wsService.notify(
                new WsResponseDTO(senderName
                        + " just make a comment to your question "
                        + commentedQuestionDTO.getTitle()),
                receiver
        );
        return Result.okOf();
    }
}
