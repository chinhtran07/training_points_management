package com.tps.components;

import com.tps.dto.RegisterMissionDTO;
import com.tps.pojo.RegisterMission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class RegisterMissionConverter {

    @Autowired
    MissionConverter missionConverter;

    public RegisterMissionDTO toDTO(RegisterMission registermission) {
        RegisterMissionDTO registerMissionDTO = new RegisterMissionDTO();
        if (registermission.getCreatedDate() == Instant.MIN) {
            registerMissionDTO.setRegisterDate(null);
        } else {
            registerMissionDTO.setRegisterDate(registermission.getCreatedDate().toString());

        }
        registerMissionDTO.setMission(missionConverter.toDTO(registermission.getMission()));
        registerMissionDTO.setIsCompleted(registermission.getIsCompleted());


        return registerMissionDTO;
    }
}
