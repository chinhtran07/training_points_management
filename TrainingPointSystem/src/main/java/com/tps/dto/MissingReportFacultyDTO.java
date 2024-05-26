package com.tps.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MissingReportFacultyDTO {
    private String lastName;
    private String firstName;
    private String studentId;
    private String missionId;
    private String createdDate;
    private boolean isActive;
}
