package com.forum.forum.dto;

import com.forum.forum.model.Notification;
import lombok.Data;

/**
 * @author ：Zack
 * @date ：Created in 2020/6/25 18:59
 */
@Data
public class NotificationDTO {
    private Long id;
    private String message;
    private Long gmtCreate;
    private Boolean isRead;
    private String redirectUri;

    public NotificationDTO(Notification notification) {
        this.id = notification.getId();
        this.message = notification.getMessage();
        this.gmtCreate = notification.getGmtCreate();
        this.isRead = notification.getIsRead();
        this.redirectUri = notification.getRedirectUri();
    }
}
