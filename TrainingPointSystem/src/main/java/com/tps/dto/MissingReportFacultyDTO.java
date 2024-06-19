package com.tps.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MissingReportFacultyDTO {
    private int id;
    private String lastName;
    private String firstName;
    private String studentId;
    private String activityName;
    private String missionName;
    //    private String missionId;
    private String status;
    private String createdDate;
    private boolean isActive;
}
