package com.tps.dto;

import lombok.Data;

import java.util.List;

@Data
public class ActivityDetailDTO {

    private int Id;
    private String Name;
    private int maxPoint;
    private PointGroupDTO pointGroup;
    private FacultyDTO faculty;
    private String periodName;
    private List<MissionDTO> missions;
}
