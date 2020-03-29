package com.forum.forum.controller;

import com.forum.forum.dto.QuestionDTO;
import com.forum.forum.dto.ResultDTO;
import com.forum.forum.model.User;
import com.forum.forum.security.jwt.JwtProvider;
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
   private JwtProvider jwtProvider;

    @Autowired
    private UserService userService;

    @GetMapping("profile/{action}")
    @ResponseBody
    public ResultDTO<QuestionDTO> profile(@PathVariable(name = "action") String action,
                          HttpServletRequest request,
                          @RequestParam(name = "offset", defaultValue = "0") Integer offset,
                          @RequestParam(name = "limit", defaultValue = "20") Integer limit) {

        String username =jwtProvider.getUserAccount(request);
        User user = userService.findByUsername(username);

        if ("questions".equals(action)) {
            List<QuestionDTO> questionDTOList = questionService.list(user.getId(), offset, limit);
            return ResultDTO.okOf(questionDTOList);

        }
        //TODO replies action
//        if ("replies".equals(action)) {
//            model.addAttribute("section", "replies");
//            model.addAttribute("sectionName", "new replies");
//        }
//
        //TODO no action error code
//        return ResultDTO.errorOf()
        return null;
    }
}


