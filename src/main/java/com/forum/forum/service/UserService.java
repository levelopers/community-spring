package com.forum.forum.service;

import com.forum.forum.exception.CustomException;
import com.forum.forum.mapper.UserMapper;
import com.forum.forum.model.User;
import com.forum.forum.model.UserExample;
import com.forum.forum.response.ResultCode;
import com.forum.forum.security.jwt.JwtProvider;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author ：Zack
 * @date ：Created in 2020/2/28 16:22
 */
@Service
public class UserService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;

//    public User createOrUpdate(User user) {
//        User dbuser = new User();
//        if (user.getId() != null) {
//            dbuser = userMapper.selectByPrimaryKey(user.getId());
//        } else if (user.getUsername() != null) {
//            dbuser = findByUsername(user.getUsername());
//        }
//        if (dbuser != null) {
//            dbuser.setUsername(user.getUsername());
//            dbuser.setToken(user.getToken());
//            dbuser.setAvatarUrl(user.getAvatarUrl());
//            dbuser.setGmtModified(System.currentTimeMillis());
//            UserExample example = new UserExample();
//            example.createCriteria().andIdEqualTo(dbuser.getId());
//            userMapper.updateByExampleSelective(dbuser, example);
//            return dbuser;
//        } else {
//            User newUser = new User();
//            newUser.setGmtCreate(System.currentTimeMillis());
//            newUser.setGmtModified(user.getGmtCreate());
//            newUser.setUsername(user.getUsername());
//            newUser.setPassword(passwordEncoder.encode(user.getPassword()));
//            userMapper.insert(newUser);
//            return newUser;
//        }
//    }

    public User createUser(User user) {
        User dbuser = findByUsername(user.getUsername());
        if (dbuser != null) {
            throw new CustomException(ResultCode.USER_HAS_EXISTED, "user.username");
        }
        User newUser = new User();
        newUser.setGmtCreate(System.currentTimeMillis());
        newUser.setGmtModified(user.getGmtCreate());
        newUser.setUsername(user.getUsername());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        userMapper.insert(newUser);
        return newUser;
    }

//    public User updateUser(User user) {
//        User dbuser = userMapper.selectByPrimaryKey(user.getId());
//        if (dbuser == null) {
//            throw new CustomException(ResultCode.USER_NOT_EXIST, "user.id");
//        }
//        dbuser.setUsername(user.getUsername());
//        dbuser.setPassword(passwordEncoder.encode(user.getPassword()));
//        dbuser.setToken(user.getToken());
//        dbuser.setAvatarUrl(user.getAvatarUrl());
//        dbuser.setGmtModified(System.currentTimeMillis());
//        UserExample example = new UserExample();
//        example.createCriteria().andIdEqualTo(dbuser.getId());
//        userMapper.updateByExampleSelective(dbuser, example);
//        return dbuser;
//    }

    public User findByUsername(String username) {
        if (StringUtils.isBlank(username)) {
            return null;
        }
        UserExample userExample = new UserExample();
        userExample.createCriteria().andUsernameEqualTo(username);
        List<User> users = userMapper.selectByExample(userExample);
        if (users.isEmpty()) {
            return null;
        }
        return users.get(0);
    }

    public User findById(Long id) {
        return userMapper.selectByPrimaryKey(id);
    }

    public User getCurrentUser(HttpServletRequest request) {
        String username = jwtProvider.getUserAccount(request);
        return findByUsername(username);
    }

//    public void uploadImage(HttpServletRequest request, byte [] imgByte) {
//        User user = this.getCurrentUser(request);
//        user.setAvatarUrl(imgByte);
//        user.setGmtModified(System.currentTimeMillis());
//        UserExample example = new UserExample();
//        example.createCriteria().andIdEqualTo(user.getId());
//        userMapper.updateByExampleSelective(user, example);
//    }
}
