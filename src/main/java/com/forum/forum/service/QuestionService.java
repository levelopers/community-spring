package com.forum.forum.service;

import com.forum.forum.dto.PaginationDTO;
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

    public PaginationDTO list(Integer page, Integer size) {
        Integer offset = size * (page - 1);
        List<Question> questionList = questionMapper.list(offset, size);
        List<QuestionDTO> questionDTOList = new ArrayList<>();

        PaginationDTO paginationDTO = new PaginationDTO();
        for (Question question : questionList) {
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            User user = userMapper.findById(question.getCreator());
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setQuestionDTOList(questionDTOList);
        Integer totalCount = questionMapper.count();
        paginationDTO.setPagination(totalCount, page);
        return paginationDTO;
    }

    public PaginationDTO list(Integer userId, Integer page, Integer size) {
        Integer offset = size * (page - 1);
        List<Question> questionList = questionMapper.listByUserId(userId,offset, size);
        List<QuestionDTO> questionDTOList = new ArrayList<>();

        PaginationDTO paginationDTO = new PaginationDTO();
        for (Question question : questionList) {
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            User user = userMapper.findById(question.getCreator());
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setQuestionDTOList(questionDTOList);
        Integer totalCount = questionMapper.countByUserID(userId);
        paginationDTO.setPagination(totalCount, page);
        return paginationDTO;
    }
}
