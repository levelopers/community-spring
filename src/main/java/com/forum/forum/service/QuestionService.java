package com.forum.forum.service;

import com.forum.forum.dto.QuestionDTO;
import com.forum.forum.exception.CustomException;
import com.forum.forum.mapper.QuestionExtMapper;
import com.forum.forum.mapper.QuestionMapper;
import com.forum.forum.model.Question;
import com.forum.forum.model.QuestionExample;
import com.forum.forum.model.User;
import com.forum.forum.response.ResultCode;
import org.apache.ibatis.session.RowBounds;
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
    private QuestionMapper questionMapper;

    @Autowired
    private QuestionExtMapper questionExtMapper;

    @Autowired
    private UserService userService;

    public List<QuestionDTO> list(Integer limit, Integer offset) {
        List<Question> questionList = questionMapper.selectByExampleWithRowbounds(new QuestionExample(), new RowBounds(offset, limit));
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        for (Question question : questionList) {
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            User user = userService.findById(question.getCreator());
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        return questionDTOList;
    }

    public List<QuestionDTO> listByCurrentUser(User currentUser, Integer offset, Integer limit) {
        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria().andCreatorEqualTo(currentUser.getId());
        List<Question> questionList = questionMapper.selectByExampleWithRowbounds(questionExample, new RowBounds(offset, limit));
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        for (Question question : questionList) {
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            User questionCreator = userService.findById(question.getCreator());
            questionDTO.setUser(questionCreator);
            questionDTOList.add(questionDTO);
        }
        return questionDTOList;
    }

    public QuestionDTO findById(Long id) {
        Question question = questionMapper.selectByPrimaryKey(id);
        if (question == null) {
            throw new CustomException(ResultCode.RESULT_DATA_NONE, "question.id");
        }
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question, questionDTO);

        User user = userService.findById(question.getCreator());
        questionDTO.setUser(user);
        return questionDTO;
    }

    public Question post(Question questionBody, User currentUser) {
        Question question = new Question();
        BeanUtils.copyProperties(questionBody, question);
        question.setCreator(currentUser.getId());

        if (question.getId() == null) {
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            question.setViewCount(0);
            question.setCommentCount(0);
            question.setLikeCount(0);
            questionMapper.insert(question);
            return question;
        } else {
            Question dbQuestion = questionMapper.selectByPrimaryKey(question.getId());
            if (dbQuestion == null) {
                throw new CustomException(ResultCode.RESULT_DATA_NONE, "question.id");
            }
            question.setGmtModified(System.currentTimeMillis());
            question.setTitle(questionBody.getTitle());
            question.setDescription(questionBody.getDescription());
            question.setTag(questionBody.getTag());
            QuestionExample example = new QuestionExample();
            example.createCriteria().andIdEqualTo(questionBody.getId());
            questionMapper.updateByExampleSelective(question, example);
            return question;
        }
    }

    public void incView(Long id) {
        Question question = new Question();
        question.setId(id);
        question.setViewCount(1);
        questionExtMapper.incView(question);
    }
}
