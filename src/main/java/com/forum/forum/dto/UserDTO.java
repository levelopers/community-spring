package com.forum.forum.dto;

import com.forum.forum.model.User;
import lombok.Data;

/**
 * @author ：Zack
 * @date ：Created in 2020/6/12 13:30
 */
@Data
public class UserDTO {
    private Long id;
    private String username;
    private String avatarUrl;

    public UserDTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.avatarUrl = user.getAvatarUrl();
    }
}
