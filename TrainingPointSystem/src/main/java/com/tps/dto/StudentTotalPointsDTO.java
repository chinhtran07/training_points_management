package com.tps.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentTotalPointsDTO {
    private int id;
    private String firstName;
    private String lastName;
    private String className;
    private String facultyName;
    private int totalPoints;
}
