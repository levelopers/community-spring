package com.forum.forum.controller;

import com.alibaba.fastjson.JSONObject;
import com.forum.forum.mapper.UserMapper;
import com.forum.forum.model.User;
import com.forum.forum.model.UserExample;
import com.forum.forum.response.Result;
import com.forum.forum.response.ResultCode;
import com.forum.forum.security.jwt.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;

/**
 * @author ：Zack
 * @date ：Created in 2020/3/27 23:12
 */
@Controller
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;
//
//    @PostMapping("/users/signup")
//    @ResponseBody
//    public void signUp(@RequestBody UserEntity userEntity) {
//        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
//        userEntity.setUserId(UUID.randomUUID().toString());
//        userJPA.save(userEntity);
//    }
//
    @RequestMapping(value = "/users/login", method = {RequestMethod.POST})
    @ResponseBody
    public Result login(@RequestBody User user, HttpServletResponse response) {
//        User user = userJPA.findByUsername(userEntity.getUsername());
        UserExample userExample = new UserExample();
        userExample.createCriteria().andUsernameEqualTo(user.getUsername());
        List<User> users = userMapper.selectByExample(userExample);
        boolean isPasswordMatched = passwordEncoder.matches(user.getPassword(), users.get(0).getPassword());
        if (isPasswordMatched) {
            String token= jwtProvider.generate(user.getUsername());
            response.addHeader("Authorization", "Bearer " + token);
            JSONObject result = new JSONObject();
            result.put("token", token);
            return Result.SUCCESS(result);
        } else {
            return Result.FAIL(ResultCode.USER_LOGIN_ERROR);
        }
    }


    @GetMapping("/users/login")
    public String loginPage() {
        return "login";
    }
}
