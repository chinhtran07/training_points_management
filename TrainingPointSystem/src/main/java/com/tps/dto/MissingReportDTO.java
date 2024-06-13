package com.tps.dto;

import lombok.Data;

import java.util.List;

@Data
public class MissingReportDTO {
    private MissionDTO mission;
    private Boolean isCompleted;
    private String registerDate;
    private String description;
    private StudentDTO student;
    private List<ImageDTO> images;
    private String status;
}
