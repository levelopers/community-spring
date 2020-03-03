package com.forum.forum.dto;

import com.forum.forum.model.User;
import lombok.Data;

/**
 * @author ：Zack
 * @date ：Created in 2020/3/2 23:55
 */
@Data
public class CommentDTO {
    private Long id;
    private Long parentId;
    private Integer type;
    private Long commentator;
    private Long gmtCreate;
    private Long gmtModified;
    private Long likeCount;
    private Integer commentCount;
    private String content;
    private User user;
}
