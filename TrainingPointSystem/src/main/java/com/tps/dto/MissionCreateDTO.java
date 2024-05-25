package com.tps.dto;

import lombok.Data;

@Data
public class MissionCreateDTO {
    private String name;
    private Integer point;
    private String content;
    private String startDate;
    private String endDate;
    private int activity;
}
