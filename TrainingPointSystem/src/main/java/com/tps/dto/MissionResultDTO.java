package com.tps.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MissionResultDTO {
    private int missionId;
    private String missionName;
    private int point;

    public MissionResultDTO(int missionId, String missionName, int point) {
        this.missionId = missionId;
        this.missionName = missionName;
        this.point = point;
    }
}
