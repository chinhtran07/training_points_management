package com.tps.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class StudentResultDTO {
    private Integer id;
    private String name;
    private String content;
    private Integer maxPoint;
    private List<ActivityResultDTO> listActivity;

    public StudentResultDTO(Integer id, String name, String content, Integer maxPoint, List<ActivityResultDTO> listActivity) {
        this.id = id;
        this.name = name;
        this.content = content;
        this.maxPoint = maxPoint;
        this.listActivity = listActivity;
    }
}
