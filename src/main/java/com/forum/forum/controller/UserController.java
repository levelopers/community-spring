package com.forum.forum.controller;

import com.alibaba.fastjson.JSONObject;
import com.forum.forum.exception.CustomException;
import com.forum.forum.model.User;
import com.forum.forum.response.Result;
import com.forum.forum.response.ResultCode;
import com.forum.forum.security.jwt.JwtProvider;
import com.forum.forum.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

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
    public Result signUp(@RequestBody User userBody) {
        User newUser = userService.createUser(userBody);
        return Result.okOf(newUser);
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
            throw new CustomException(ResultCode.USER_LOGIN_ERROR, "user.username / user.password");
        }
    }

    @GetMapping("/user")
    @ResponseBody
    public Result<User> user(HttpServletRequest request) {
        User user = userService.getCurrentUser(request);
        return Result.okOf(user);
    }

//    @PutMapping("/user")
//    @ResponseBody
//    public Result<User> updateUser(@RequestBody User userBody) {
//        User userResult = userService.updateUser(userBody);
//        return Result.okOf(userResult);
//    }

    @PostMapping("/user/upload")
    public Result uplaodImage(@RequestParam("imageFile") MultipartFile file,
                              HttpServletRequest request) throws IOException {
        byte [] imgByte = compressBytes(file.getBytes());
        this.userService.uploadImage(request ,imgByte);

        return Result.okOf();
    }
    @GetMapping(path = { "/user/image" })
    public Result getImage(HttpServletRequest request) throws IOException {
        User dbUser = userService.getCurrentUser(request);
        byte[] img = decompressBytes(dbUser.getAvatarUrl());
        return Result.okOf(img);
    }
    // compress the image bytes before storing it in the database
    public static byte[] compressBytes(byte[] data) {
        Deflater deflater = new Deflater();
        deflater.setInput(data);
        deflater.finish();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        while (!deflater.finished()) {
            int count = deflater.deflate(buffer);
            outputStream.write(buffer, 0, count);
        }
        try {
            outputStream.close();
        } catch (IOException e) {
        }
        System.out.println("Compressed Image Byte Size - " + outputStream.toByteArray().length);
        return outputStream.toByteArray();
    }
    // uncompress the image bytes before returning it to the angular application
    public static byte[] decompressBytes(byte[] data) {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(buffer);
                outputStream.write(buffer, 0, count);
            }
            outputStream.close();
        } catch (IOException ioe) {
        } catch (DataFormatException e) {
        }
        return outputStream.toByteArray();
    }

}
