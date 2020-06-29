package com.forum.forum.dto;

import com.forum.forum.model.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

/**
 * @author ：Zack
 * @date ：Created in 2020/6/12 13:30
 */
@Data
@NoArgsConstructor
public class UserDTO {
    private Long id;
    private String username;
    private String avatarUrl;
    private String password;

    public UserDTO(User user) {
        this.id = user.getUserId();
        this.username = user.getUsername();
        this.avatarUrl = user.getAvatarUrl();
    }

    public static User toUser(UserDTO userDTO) {
        User user = new User();
        user.setUserId(userDTO.getId());
        user.setUsername(userDTO.getUsername());
        user.setAvatarUrl(userDTO.getAvatarUrl());
        user.setPassword(userDTO.getPassword().orElse(""));
        return user;
    }

    public Optional<String> getPassword() {
        return Optional.ofNullable(password);
    }
}
