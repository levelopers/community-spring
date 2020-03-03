package com.forum.forum.dto;

import lombok.Data;

/**
 * @author ：Zack
 * @date ：Created in 2020/3/2 22:39
 */
@Data
public class CommentCreateDTO {
    private Long parentId;
    private String content;
    private Integer type;
}
