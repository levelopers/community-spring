package com.forum.forum.service;

import com.forum.forum.dto.CommentDTO;
import com.forum.forum.enums.CommentTypeEnum;
import com.forum.forum.exception.CustomException;
import com.forum.forum.mapper.*;
import com.forum.forum.model.*;
import com.forum.forum.response.ResultCode;
import com.forum.forum.security.jwt.JwtProvider;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author ：Zack
 * @date ：Created in 2020/2/29 18:22
 */
@Service
public class CommentService {

    @Autowired
    CommentMapper commentMapper;

    @Autowired
    CommentExtMapper commentExtMapper;

    @Autowired
    QuestionMapper questionMapper;

    @Autowired
    QuestionExtMapper questionExtMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private UserService userService;

    @Transactional
    public Comment post(Comment comment, User currentUser) {

        if (comment.getParentId() == null || comment.getParentId() == 0) {
            throw new CustomException(ResultCode.PARAM_NOT_COMPLETE, "comment.parentId");
        }
        if (comment.getType() == null || !CommentTypeEnum.isExist(comment.getType())) {
            throw new CustomException(ResultCode.PARAM_NOT_COMPLETE, "comment.type");
        }

        if (comment.getId() == null) {
            comment.setCommentator(currentUser.getId());
            comment.setGmtCreate(System.currentTimeMillis());
            comment.setGmtModified(comment.getGmtCreate());
            comment.setCommentCount(0);
            comment.setLikeCount(0L);
            commentMapper.insert(comment);
            return comment;
        }
        if(comment.getId()!=null) {
            Comment dbComment = commentMapper.selectByPrimaryKey(comment.getId());
            if (dbComment == null) {
                throw new CustomException(ResultCode.PARAM_IS_INVALID, "comment.id");
            }
            if (dbComment.getCommentator() != currentUser.getId()) {
                throw new CustomException(ResultCode.USER_NOT_EXIST, "comment.commentator");
            }
            Comment updateComment = new Comment();
            BeanUtils.copyProperties(dbComment,updateComment);
            updateComment.setGmtModified(System.currentTimeMillis());
            updateComment.setContent(comment.getContent());
            CommentExample example = new CommentExample();
            example.createCriteria().andIdEqualTo(comment.getId());
            commentMapper.updateByExampleSelective(updateComment, example);
            return updateComment;
        }
        incCommentCount(comment);
        return null;
    }

    public List<CommentDTO> listByTargetId(Long id, CommentTypeEnum type) {
        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria()
                .andParentIdEqualTo(id)
                .andTypeEqualTo(type.getType());
        commentExample.setOrderByClause("gmt_create asc");
        List<Comment> comments = commentMapper.selectByExample(commentExample);

        if (comments.size() == 0) {
            return new ArrayList<>();
        }

        Set<Long> commentators = comments.stream().map(comment -> comment.getCommentator()).collect(Collectors.toSet());
        List<Long> userIds = new ArrayList();
        userIds.addAll(commentators);

        UserExample userExample = new UserExample();
        userExample.createCriteria()
                .andIdIn(userIds);
        List<User> users = userMapper.selectByExample(userExample);
        Map<Long, User> userMap = users.stream().collect(Collectors.toMap(user -> user.getId(), user -> user));

        List<CommentDTO> commentDTOS = comments.stream().map(comment -> {
            CommentDTO commentDTO = new CommentDTO();
            BeanUtils.copyProperties(comment, commentDTO);
            commentDTO.setUser(userMap.get(comment.getCommentator()));
            return commentDTO;
        }).collect(Collectors.toList());

        return commentDTOS;
    }

    private void incCommentCount(Comment comment) {
        if (comment.getType() == CommentTypeEnum.COMMENT.getType()) {
            Comment dbComment = commentMapper.selectByPrimaryKey(comment.getParentId());
            if (dbComment == null) {
                throw new CustomException(ResultCode.RESULT_DATA_NONE, "comment.parentId");
            }
            Comment parentComment = new Comment();
            parentComment.setId(comment.getParentId());
            parentComment.setCommentCount(1);
            commentExtMapper.incCommentCount(parentComment);
        }
        if (comment.getType() == CommentTypeEnum.QUESTION.getType()) {
            Question dbQuestion = questionMapper.selectByPrimaryKey(comment.getParentId());
            if (dbQuestion == null) {
                throw new CustomException(ResultCode.RESULT_DATA_NONE, "comment.parentId");
            }
            dbQuestion.setCommentCount(1);
            questionExtMapper.incCommentCount(dbQuestion);
        }
    }

}
