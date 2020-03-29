package com.forum.forum.controller;

import com.forum.forum.dto.CommentDTO;
import com.forum.forum.dto.QuestionDTO;
import com.forum.forum.dto.ResultDTO;
import com.forum.forum.enums.CommentTypeEnum;
import com.forum.forum.service.CommentService;
import com.forum.forum.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author ：Zack
 * @date ：Created in 2020/2/28 15:50
 */
@Controller
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @Autowired
    CommentService commentService;

    @GetMapping("/questions/{id}")
    @ResponseBody
    public ResultDTO<QuestionDTO> question(@PathVariable(name = "id") Long id) {
        QuestionDTO questionDTO = questionService.findById(id);
        questionService.incView(id);
        return ResultDTO.okOf(questionDTO);
    }

    @GetMapping("/questions/{id}/comments")
    @ResponseBody
    public ResultDTO<List<CommentDTO>> comments(@PathVariable(name = "id") Long id) {
        List<CommentDTO> commentDTOS = commentService.listByTargetId(id, CommentTypeEnum.QUESTION);
        //TODO inc view logic
//        questionService.incView(id);
        return ResultDTO.okOf(commentDTOS);
    }

    @GetMapping("/questions")
    @ResponseBody
    public ResultDTO<List<QuestionDTO>> index(@RequestParam(name = "limit", defaultValue = "20") Integer limit,
                                        @RequestParam(name = "offset", defaultValue = "0") Integer offset) {

        List<QuestionDTO> questionDTOList = questionService.list(limit, offset);
        return ResultDTO.okOf(questionDTOList);
    }
}
