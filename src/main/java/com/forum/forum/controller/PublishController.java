package com.forum.forum.controller;

import com.forum.forum.dto.ResultDTO;
import com.forum.forum.mapper.UserMapper;
import com.forum.forum.model.Question;
import com.forum.forum.model.User;
import com.forum.forum.model.UserExample;
import com.forum.forum.security.jwt.JwtProvider;
import com.forum.forum.service.QuestionService;
import com.forum.forum.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author ：Zack
 * @date ：Created in 2020/2/27 10:56
 */
@Controller
public class PublishController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private UserService userService;

    @PostMapping("/publish")
    @ResponseBody
    public ResultDTO<Question> doPublish(@RequestBody Question questionBody,
                                         HttpServletRequest request) {

        String username = jwtProvider.getUserAccount(request);
        User user = userService.findByUsername(username);

        Question question = new Question();
        BeanUtils.copyProperties(questionBody, question);
        question.setCreator(user.getId());
        Question questionResult = questionService.createOrUpdate(question);
        return ResultDTO.okOf(questionResult);
    }

}
