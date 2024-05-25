package com.tps.dto;

import lombok.Data;

@Data
public class PostCreateDTO {
    private String content;
    private int activity;
    private int assistant;
}
