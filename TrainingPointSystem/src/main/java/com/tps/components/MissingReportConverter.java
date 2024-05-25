package com.tps.components;

import com.tps.dto.MissingReportDTO;
import com.tps.dto.RegisterMissionDTO;
import com.tps.pojo.Missingreport;
import com.tps.pojo.Registermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MissingReportConverter {
    @Autowired
    MissionConverter missionConverter;
    public MissingReportDTO toDTO(Missingreport missingreport) {
        MissingReportDTO missingReportDTO = new MissingReportDTO();
        missingReportDTO.setRegisterDate(missingreport.getCreatedDate().toString());
        missingReportDTO.setMission(missionConverter.toDTO(missingreport.getMission()) );
        missingReportDTO.setDescription(missingreport.getDescription());

        return missingReportDTO;
    }
}
