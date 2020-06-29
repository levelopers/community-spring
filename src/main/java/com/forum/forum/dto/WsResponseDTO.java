package com.forum.forum.dto;

import lombok.Data;

/**
 * @author ：Zack
 * @date ：Created in 2020/6/20 21:02
 */
@Data
public class WsResponseDTO {
    private String message;
    private String senderName;
    private String url;
}

