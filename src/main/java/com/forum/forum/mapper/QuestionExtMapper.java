package com.forum.forum.mapper;

import com.forum.forum.model.Question;

public interface QuestionExtMapper {
    int incView(Question record);

    void incCommentCount(Question record);
}