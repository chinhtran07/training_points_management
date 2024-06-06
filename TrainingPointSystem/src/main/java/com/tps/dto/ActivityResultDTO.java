package com.tps.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ActivityResultDTO {
    private int activityId;
    private String activityName;
    private int maxPoint;
    private List<MissionResultDTO> missionResultDTOList;

    public ActivityResultDTO(int activityId, String activityName, int maxPoint, List<MissionResultDTO> missionResultDTOList) {
        this.activityId = activityId;
        this.activityName = activityName;
        this.maxPoint = maxPoint;
        this.missionResultDTOList = missionResultDTOList;
    }
}
