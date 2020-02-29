package com.forum.forum.controller;

import com.forum.forum.dto.QuestionDTO;
import com.forum.forum.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author ：Zack
 * @date ：Created in 2020/2/28 15:50
 */
@Controller
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") Integer id,
                           Model model) {
        QuestionDTO questionDTO = questionService.findById(id);
        questionService.incView(id);
        model.addAttribute("question", questionDTO);
        return "question";
    }
}
