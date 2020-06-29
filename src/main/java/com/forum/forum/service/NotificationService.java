package com.forum.forum.service;

import com.forum.forum.dto.NotificationDTO;
import com.forum.forum.dto.QuestionDTO;
import com.forum.forum.mapper.NotificationMapper;
import com.forum.forum.model.Notification;
import com.forum.forum.model.NotificationExample;
import com.forum.forum.model.Question;
import com.forum.forum.model.User;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ：Zack
 * @date ：Created in 2020/6/25 18:57
 */
@Service
public class NotificationService {

    @Autowired
    private NotificationMapper notificationMapper;

    public List<NotificationDTO> listByCurrentUser(User currentUser, Integer limit, Integer offset) {
        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria().andReceiverIdEqualTo(currentUser.getUserId());
        notificationExample.setOrderByClause("GMT_CREATE DESC");
        List<Notification> notificationList = notificationMapper.selectByExampleWithRowbounds(notificationExample, new RowBounds(offset, limit));
        List<NotificationDTO> notificationDTOList= new ArrayList<>();
        for (Notification notification: notificationList) {
            notificationDTOList.add(new NotificationDTO(notification));
        }
        return notificationDTOList;
    }

    public int save(Notification notification) {
       return notificationMapper.insert(notification);
    }

    public int readNotification(Long id) {
        Notification dbNotification = notificationMapper.selectByPrimaryKey(id);
        dbNotification.setIsRead(true);
        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria().andNotificationIdEqualTo(id);
        return notificationMapper.updateByExampleSelective(dbNotification,notificationExample);
    }
}

