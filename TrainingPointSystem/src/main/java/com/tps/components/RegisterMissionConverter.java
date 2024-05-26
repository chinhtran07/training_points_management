package com.tps.components;

import com.tps.dto.RegisterMissionDTO;
import com.tps.pojo.Registermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RegisterMissionConverter {

    @Autowired
    MissionConverter missionConverter;
    public RegisterMissionDTO toDTO(Registermission registermission) {
        RegisterMissionDTO registerMissionDTO = new RegisterMissionDTO();
        registerMissionDTO.setRegisterDate(registermission.getCreatedDate().toString());
        registerMissionDTO.setMission(missionConverter.toDTO(registermission.getMission()) );
        registerMissionDTO.setIsCompleted(registermission.getIsCompleted());

        return registerMissionDTO;
    }
}
