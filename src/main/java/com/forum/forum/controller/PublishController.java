package com.forum.forum.controller;

import com.forum.forum.dto.QuestionDTO;
import com.forum.forum.model.Question;
import com.forum.forum.model.User;
import com.forum.forum.response.Result;
import com.forum.forum.service.QuestionService;
import com.forum.forum.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author ：Zack
 * @date ：Created in 2020/2/27 10:56
 */
@Controller
public class PublishController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private UserService userService;

    @PostMapping("/publish")
    @ResponseBody
    public Result<QuestionDTO> doPublish(@RequestBody Question questionBody,
                                      HttpServletRequest request) {
        User currentUser = userService.getCurrentUser(request);
        QuestionDTO questionResult = questionService.post(questionBody, currentUser);
        return Result.okOf(questionResult);
    }

}
