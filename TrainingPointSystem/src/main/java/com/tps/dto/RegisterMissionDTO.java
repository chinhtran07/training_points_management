package com.tps.dto;

import lombok.Data;

@Data
public class RegisterMissionDTO {
    private MissionDTO mission;
    private Boolean isCompleted;
    private String registerDate;
}
