package com.tps.dto;

import lombok.Data;

import java.util.List;

@Data
public class ActivityDetailDTO {

    private int Id;
    private String Name;
    private int maxPoint;
    private PointGroupDTO pointGroup;
    private List<MissionDTO> missions;
}
