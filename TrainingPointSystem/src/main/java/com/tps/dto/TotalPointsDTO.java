package com.tps.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TotalPointsDTO {
    private String facultyName;
    private Long totalStudents;
    private Double avgTotalPoints;
}
