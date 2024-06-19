package com.tps.dto;

import lombok.Data;

@Data
public class MissionDTO {
    private int id;
    private String name;
    private Integer point;
    private String content;
    private String startDate;
    private String endDate;
//    private int pointGroup;
//    private String semester;
    private ActivityDTO activity;
}
