package com.tps.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TotalPointsDTO {
    private String facultyName;
    private Long excellent;
    private Long good;
    private Long fair;
    private Long average;
    private Long weak;
    private Long poor;
    private Long totalStudents;
    private Double avgTotalPoints;
}
