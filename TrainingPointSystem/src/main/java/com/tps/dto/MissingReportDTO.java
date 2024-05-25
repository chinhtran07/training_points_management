package com.tps.dto;

import lombok.Data;

@Data
public class MissingReportDTO {
    private MissionDTO mission;
    private Boolean isCompleted;
    private String registerDate;
    private String description;
}
