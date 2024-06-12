package com.tps.components;

import com.tps.dto.MissionCreateDTO;
import com.tps.dto.MissionDTO;
import com.tps.pojo.Mission;
import com.tps.services.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class MissionConverter {
    @Autowired
    ActivityConverter activityConverter;

    @Autowired
    ActivityService activityService;

    public MissionDTO toDTO(Mission mission) {
        MissionDTO dto = new MissionDTO();
        dto.setId(mission.getId());
        dto.setName(mission.getName());
        dto.setContent(mission.getContent());
        dto.setPoint(mission.getPoint());
        dto.setStartDate(mission.getStartDate());
        dto.setEndDate(mission.getEndDate());
//        dto.setActivity(activityConverter.toDTO(mission.getActivity()));
        return dto;
    }

    public Mission toEntity(MissionDTO missionDTO) {
        Mission mission = new Mission();
        mission.setName(missionDTO.getName());
        mission.setContent(missionDTO.getContent());
//        mission.setActivity(activityConverter.toEntity(missionDTO.getActivity()));
        mission.setPoint(missionDTO.getPoint());
        mission.setStartDate(missionDTO.getStartDate());
        mission.setEndDate(missionDTO.getEndDate());

        return mission;
    }

    public Mission toEntity(MissionCreateDTO missionDTO) {
        Mission mission = new Mission();
        mission.setName(missionDTO.getName());
        mission.setContent(missionDTO.getContent());
        mission.setActivity(activityService.getActivityById(missionDTO.getActivity()));
        mission.setPoint(missionDTO.getPoint());
        mission.setStartDate(LocalDate.parse(missionDTO.getStartDate()));
        mission.setEndDate(LocalDate.parse(missionDTO.getEndDate()));

        return mission;
    }
}
