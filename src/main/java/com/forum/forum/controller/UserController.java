package com.forum.forum.controller;

import com.alibaba.fastjson.JSONObject;
import com.forum.forum.dto.UserDTO;
import com.forum.forum.exception.CustomException;
import com.forum.forum.model.User;
import com.forum.forum.response.Result;
import com.forum.forum.response.ResultCode;
import com.forum.forum.security.jwt.JwtProvider;
import com.forum.forum.service.UploadService;
import com.forum.forum.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
    private UploadService uploadService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/users/signup")
    @ResponseBody
    public Result<UserDTO> signUp(@RequestBody User userBody) {
        User newUser = userService.createUser(userBody);
        UserDTO userDTO = new UserDTO(newUser);
        return Result.okOf(userDTO);
    }

    @RequestMapping(value = "/users/login", method = {RequestMethod.POST})
    @ResponseBody
    public Result login(@RequestBody User userBody, HttpServletResponse response) {
        User dbUser = userService.findByUsername(userBody.getUsername());
        boolean isPasswordMatched = passwordEncoder.matches(userBody.getPassword(), dbUser.getPassword());
        if (isPasswordMatched) {
            String token = jwtProvider.generate(dbUser.getUsername());
            response.addHeader("Authorization", "Bearer " + token);
            JSONObject result = new JSONObject();
            result.put("token", token);
            result.put("username", dbUser.getUsername());
            return Result.okOf(result);
        } else {
            throw new CustomException(ResultCode.USER_LOGIN_ERROR, "user.username / user.password");
        }
    }

    @GetMapping("/user")
    @ResponseBody
    public Result<UserDTO> user(HttpServletRequest request) {
        User user = userService.getCurrentUser(request);
        UserDTO userDTO = new UserDTO(user);
        return Result.okOf(userDTO);
    }

    @PostMapping("/user")
    @ResponseBody
    public Result<UserDTO> updateUser(@RequestBody User userBody) {
        User updatedUser = userService.updateUser(userBody);
        UserDTO userDTO = new UserDTO(updatedUser);
        return Result.okOf(userDTO);
    }

    @PostMapping("/user/upload")
    @ResponseBody
    public Result<UserDTO> uplaodImage(MultipartFile file, HttpServletRequest request) throws IOException {
        String url = uploadService.uploadObject(file);
        User dbuser = this.userService.getCurrentUser(request);
        User user = new User();
        user.setId(dbuser.getId());
        user.setAvatarUrl(url);
        User updatedUser = this.userService.updateUser(user);
        UserDTO userDTO = new UserDTO(updatedUser);
        return Result.okOf(userDTO);
    }
}
