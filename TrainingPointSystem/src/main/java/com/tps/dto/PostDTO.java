package com.tps.dto;

import lombok.Data;

import java.util.List;

@Data
public class PostDTO {
    private int id;
    private String content;
    private ActivityDTO activity;
    private UserAssistantDTO assistant;
    private String createdDate;
    private List<ImageDTO> images;
}
