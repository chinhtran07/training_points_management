package com.tps.dto;

import lombok.Data;

@Data
public class MissingReportStudentDTO {
    private String pointGroupName;
    private String activityName;
    private String missionId;
    private String missionName;
    private Integer missionPoint;
    private String description;
    private String status;
}
