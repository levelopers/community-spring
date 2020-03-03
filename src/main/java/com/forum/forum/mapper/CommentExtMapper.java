package com.forum.forum.mapper;

import com.forum.forum.model.Comment;

/**
 * @author ：Zack
 * @date ：Created in 2020/3/3 15:33
 */
public interface CommentExtMapper {
    int incCommentCount(Comment record);
}
