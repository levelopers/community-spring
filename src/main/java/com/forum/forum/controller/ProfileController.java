package com.forum.forum.controller;

import com.forum.forum.dto.QuestionDTO;
import com.forum.forum.exception.CustomException;
import com.forum.forum.model.User;
import com.forum.forum.response.Result;
import com.forum.forum.response.ResultCode;
import com.forum.forum.service.QuestionService;
import com.forum.forum.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author ：Zack
 * @date ：Created in 2020/2/28 10:08
 */
@Controller
public class ProfileController {

    @Autowired
   private QuestionService questionService;

    @Autowired
    private UserService userService;

    @GetMapping("profile/{action}")
    @ResponseBody
    public Result<QuestionDTO> profile(@PathVariable(name = "action") String action,
                          HttpServletRequest request,
                          @RequestParam(name = "offset", defaultValue = "0") Integer offset,
                          @RequestParam(name = "limit", defaultValue = "20") Integer limit) {

        if ("questions".equals(action)) {
            User currentUser = userService.getCurrentUser(request);
            List<QuestionDTO> questionDTOList = questionService.listByCurrentUser(currentUser, offset, limit);
            return Result.okOf(questionDTOList);

        } else {
            throw new CustomException(ResultCode.PARAM_IS_INVALID,"path variable action");
        }
        //TODO replies action
//        if ("replies".equals(action)) {
//            model.addAttribute("section", "replies");
//            model.addAttribute("sectionName", "new replies");
//        }
    }
}


