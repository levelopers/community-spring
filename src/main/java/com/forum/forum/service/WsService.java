package com.forum.forum.service;

import com.forum.forum.dto.WsResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

/**
 * @author ：Zack
 * @date ：Created in 2020/6/19 22:16
 */
@Service
public class WsService {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public void notify(WsResponseDTO wsResponseDTO, String username) {
        messagingTemplate.convertAndSendToUser(
                username,
                "/queue/notify",
                wsResponseDTO
        );
    }
}
