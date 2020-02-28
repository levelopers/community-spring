package com.forum.forum.service;

import com.forum.forum.mapper.UserMapper;
import com.forum.forum.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ：Zack
 * @date ：Created in 2020/2/28 16:22
 */
@Service
public class UserService {

    @Autowired
    UserMapper userMapper;

    public void createOrUpdate(User user) {
        User dbuser = userMapper.findByAccountId(user.getAccountId());
        if (dbuser != null) {
            dbuser.setName(user.getName());
            dbuser.setToken(user.getToken());
            dbuser.setAvatarUrl(user.getAvatarUrl());
            dbuser.setGmtModified(System.currentTimeMillis());
            userMapper.update(dbuser);
        } else {
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
        }
    }
}
