package com.tps.dto;

import lombok.Data;

@Data
public class ChatMessages {
    private String sender;
    private String message;
    private Long timestamp;
}
