package com.forum.forum.dto;

import lombok.Data;

/**
 * @author ：Zack
 * @date ：Created in 2020/2/27 19:25
 */
@Data
public class QuestionDTO {
    private Long id;
    private String title;
    private String description;
    private String tag;
    private Long gmtCreate;
    private Long gmtModified;
    private Long creator;
    private Integer viewCount;
    private Integer commentCount;
    private Integer likeCount;
    private UserDTO user;
}
