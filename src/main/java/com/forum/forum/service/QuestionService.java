package com.forum.forum.service;

import com.forum.forum.dto.QuestionDTO;
import com.forum.forum.dto.UserDTO;
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
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    @Cacheable(cacheNames = "questions_all")
    public List<QuestionDTO> list(Integer offset, Integer limit) {
        QuestionExample questionExample = new QuestionExample();
        questionExample.setOrderByClause("GMT_MODIFIED DESC");
        List<Question> questionList = questionMapper.selectByExampleWithRowbounds(questionExample, new RowBounds(offset, limit));
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        for (Question question : questionList) {
            QuestionDTO questionDTO = getQuestionDTO(question);
            questionDTOList.add(questionDTO);
        }
        return questionDTOList;
    }

    @Cacheable(value = "questions_by_userId", key = "#currentUser.userId")
    public List<QuestionDTO> listByCurrentUser(User currentUser, Integer offset, Integer limit) {
        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria().andCreatorIdEqualTo(currentUser.getUserId());
        questionExample.setOrderByClause("GMT_MODIFIED DESC");
        List<Question> questionList = questionMapper.selectByExampleWithRowbounds(questionExample, new RowBounds(offset, limit));
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        for (Question question : questionList) {
            QuestionDTO questionDTO = getQuestionDTO(question);
            questionDTOList.add(questionDTO);
        }
        return questionDTOList;
    }

    @Cacheable(value = "questions_by_tag", key = "#tagName")
    public List<QuestionDTO> listByTag(String tagName, Integer offset, Integer limit) {
        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria().andTagLike(new StringBuilder().append("%").append(tagName).append("%").toString());
//        TODO Extending the Example Classes
        questionExample.setOrderByClause("VIEW_COUNT, LIKE_COUNT DESC");
        List<Question> questions = questionMapper.selectByExampleWithRowbounds(questionExample, new RowBounds(offset, limit));
        List<QuestionDTO> questionDTOS = questions
                .stream()
                .map(this::getQuestionDTO)
                .collect(Collectors.toList());
        return questionDTOS;
    }

    @Cacheable(value = "question_by_id", key = "#id")
    public QuestionDTO findById(Long id) {
        Question question = questionMapper.selectByPrimaryKey(id);
        if (question == null) {
            throw new CustomException(ResultCode.RESULT_DATA_NONE, "question.id");
        }
        QuestionDTO questionDTO = getQuestionDTO(question);
        return questionDTO;
    }

    public QuestionDTO post(Question questionBody, User currentUser) {
        Question question = new Question();
        BeanUtils.copyProperties(questionBody, question);
        question.setCreatorId(currentUser.getUserId());

        if (question.getQuestionId() == null) {
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            question.setViewCount(0);
            question.setCommentCount(0);
            question.setLikeCount(0);
            questionMapper.insert(question);
            return getQuestionDTO(question);
        } else {
            Question dbQuestion = questionMapper.selectByPrimaryKey(question.getQuestionId());
            if (dbQuestion == null) {
                throw new CustomException(ResultCode.RESULT_DATA_NONE, "question.id");
            }
            question.setGmtModified(System.currentTimeMillis());
            question.setTitle(questionBody.getTitle());
            question.setDescription(questionBody.getDescription());
            question.setTag(questionBody.getTag());
            QuestionExample example = new QuestionExample();
            example.createCriteria().andQuestionIdEqualTo(questionBody.getQuestionId());
            questionMapper.updateByExampleSelective(question, example);
            return getQuestionDTO(question);
        }
    }

    public void incView(Long id) {
        Question question = new Question();
        question.setQuestionId(id);
        question.setViewCount(1);
        questionExtMapper.incView(question);
    }

    private QuestionDTO getQuestionDTO(Question question) {
        QuestionDTO questionDTO = new QuestionDTO(question);
        User user = userService.findById(question.getCreatorId());
        questionDTO.setCreator(new UserDTO(user));
        return questionDTO;
    }
}
