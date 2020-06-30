package com.forum.forum.controller;

import com.forum.forum.dto.NotificationDTO;
import com.forum.forum.model.User;
import com.forum.forum.response.Result;
import com.forum.forum.service.NotificationService;
import com.forum.forum.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author ：Zack
 * @date ：Created in 2020/6/25 18:54
 */
@Controller
public class NotificationController {

    @Autowired
    private UserService userService;

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/notifications")
    @ResponseBody
    public Result<NotificationDTO> notifications(@RequestParam(name = "limit", defaultValue = "20") Integer limit,
                                @RequestParam(name = "offset", defaultValue = "0") Integer offset,
                                HttpServletRequest request) {
        User user = userService.getCurrentUser(request);
        List<NotificationDTO> notificationDTOList = notificationService.listByCurrentUser(user,limit,offset);
        return Result.okOf(notificationDTOList);
    }

    @PostMapping("/notifications/{id}/read")
    @ResponseBody
    public Result notifications(@PathVariable(name = "id") Long id) {
        notificationService.readNotification(id);
        return Result.okOf();
    }
}
