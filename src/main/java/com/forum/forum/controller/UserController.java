package com.forum.forum.controller;

import com.alibaba.fastjson.JSONObject;
import com.forum.forum.model.User;
import com.forum.forum.response.Result;
import com.forum.forum.response.ResultCode;
import com.forum.forum.security.jwt.JwtProvider;
import com.forum.forum.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author ：Zack
 * @date ：Created in 2020/3/27 23:12
 */
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/users/signup")
    @ResponseBody
    public User signUp(@RequestBody User userBody) {
        User userResult = userService.createOrUpdate(userBody);
        return  userResult;
    }

    @RequestMapping(value = "/users/login", method = {RequestMethod.POST})
    @ResponseBody
    public Result login(@RequestBody User userBody, HttpServletResponse response) {
        User dbUser = userService.findByUsername(userBody.getUsername());
        boolean isPasswordMatched = passwordEncoder.matches(userBody.getPassword(),dbUser.getPassword());
        if (isPasswordMatched) {
            String token= jwtProvider.generate(dbUser.getUsername());
            response.addHeader("Authorization", "Bearer " + token);
            JSONObject result = new JSONObject();
            result.put("token", token);
            return Result.okOf(result);
        } else {
            return Result.errorOf(ResultCode.USER_LOGIN_ERROR);
        }
    }

    @GetMapping("/user")
    @ResponseBody
    public Result<User> user(HttpServletRequest request) {
        User user = userService.getCurrentUser(request);
        return Result.okOf(user);
    }
}
