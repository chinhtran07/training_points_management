package com.tps.dto;

import lombok.Data;

@Data
public class CommentDTO {
    private int id;
    private String content;
    private UserDTO user;
    private String createdAt;
}
