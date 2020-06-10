package com.forum.forum.controller;

import com.forum.forum.dto.CommentDTO;
import com.forum.forum.enums.CommentTypeEnum;
import com.forum.forum.model.Comment;
import com.forum.forum.model.User;
import com.forum.forum.response.Result;
import com.forum.forum.service.CommentService;
import com.forum.forum.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author ：Zack
 * @date ：Created in 2020/2/29 15:25
 */
@Controller
public class CommentController {

    @Autowired
    CommentService commentService;

    @Autowired
    private UserService userService;

    @ResponseBody
    @PostMapping("/comment")
    public Object post(@RequestBody Comment comment,
                       HttpServletRequest request) {
        User currentUser = userService.getCurrentUser(request);
        Comment commentResult = commentService.post(comment, currentUser);
        return Result.okOf(commentResult);
    }

    /**
     * @description get all subcomments by parent comment id
     * @param id
     * @return
     */
    @ResponseBody
    @GetMapping("/comment/{id}")
    public Result<List<CommentDTO>> comments(@PathVariable(name = "id") Long id) {
        List<CommentDTO> commentDTOS = commentService.listByTargetId(id, CommentTypeEnum.COMMENT);
        return Result.okOf(commentDTOS);
    }

    @ResponseBody
    @PostMapping("/comment/{id}/like")
    public Result incLikeCount(@PathVariable(name = "id") Long id) {
        // TODO db record track who liked
        int result = commentService.incLikeCount(id);
        return Result.okOf(result);
    }

    @ResponseBody
    @PostMapping("/comment/{id}/dislike")
    public Result decLikeCount(@PathVariable(name = "id") Long id) {
        int result = commentService.decLikeCount(id);
        return Result.okOf(result);
    }

}
