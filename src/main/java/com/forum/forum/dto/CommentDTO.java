package com.forum.forum.dto;

import com.forum.forum.model.Comment;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ：Zack
 * @date ：Created in 2020/3/2 23:55
 */
@Data
@NoArgsConstructor
public class CommentDTO {
    private Long id;
    private Long parentId;
    private Integer type;
    private Long gmtCreate;
    private Long gmtModified;
    private Long likeCount;
    private Integer commentCount;
    private String content;
    private Long commentatorId;
    private UserDTO commentator;
    public CommentDTO(Comment comment) {
        this.id = comment.getCommentId();
        this.parentId = comment.getParentId();
        this.type = comment.getType();
        this.gmtCreate = comment.getGmtCreate();
        this.gmtModified = comment.getGmtModified();
        this.likeCount = comment.getLikeCount();
        this.commentCount = comment.getCommentCount();
        this.content = comment.getContent();
        this.commentatorId = comment.getCommentatorId();
    }
}
