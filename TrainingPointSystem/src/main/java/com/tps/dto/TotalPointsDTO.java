package com.tps.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TotalPointsDTO {
    private String facultyName;
    private int excellent;
    private int good;
    private int fair;
    private int average;
    private int weak;
    private int poor;
    private int totalStudents;
    private int avgTotalPoints;
}
