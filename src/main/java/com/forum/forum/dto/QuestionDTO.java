package com.forum.forum.dto;

import com.forum.forum.model.Question;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ：Zack
 * @date ：Created in 2020/2/27 19:25
 */
@Data
@NoArgsConstructor
public class QuestionDTO {
    private Long id;
    private String title;
    private String description;
    private String tag;
    private Long gmtCreate;
    private Long gmtModified;
    private Integer viewCount;
    private Integer commentCount;
    private Integer likeCount;
    private Long creatorId;
    private UserDTO creator;
    public QuestionDTO(Question question) {
        this.id = question.getQuestionId();
        this.title = question.getTitle();
        this.description = question.getDescription();
        this.tag = question.getTag();
        this.gmtCreate = question.getGmtCreate();
        this.gmtModified = question.getGmtModified();
        this.viewCount = question.getViewCount();
        this.commentCount = question.getCommentCount();
        this.likeCount = question.getLikeCount();
        this.creatorId = question.getCreatorId();
    }
}
