package com.forum.forum.dto;

import com.forum.forum.model.Comment;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author ：Zack
 * @date ：Created in 2020/3/2 23:55
 */
@Data
@NoArgsConstructor
public class CommentDTO implements Serializable {
    private Long id;
    private Long likeCount;
    private Integer commentCount;
    private String content;
    private UserDTO commentator;
    public CommentDTO(Comment comment) {
        this.id = comment.getCommentId();
        this.likeCount = comment.getLikeCount();
        this.commentCount = comment.getCommentCount();
        this.content = comment.getContent();
    }
}
