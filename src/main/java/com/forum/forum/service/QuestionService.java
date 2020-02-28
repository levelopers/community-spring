package com.forum.forum.service;

import com.forum.forum.dto.QuestionDTO;
import com.forum.forum.mapper.QuestionMapper;
import com.forum.forum.mapper.UserMapper;
import com.forum.forum.model.Question;
import com.forum.forum.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ：Zack
 * @date ：Created in 2020/2/27 19:26
 */
@Service
public class QuestionService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionMapper questionMapper;

    public List<QuestionDTO> list() {
        List<Question> questionList = questionMapper.list();
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        for (Question question : questionList) {
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            User user = userMapper.findById(question.getCreator());
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        return questionDTOList;
    }
}
