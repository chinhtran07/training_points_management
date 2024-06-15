package com.tps.components;

import com.tps.dto.MissingReportDTO;
import com.tps.dto.MissingReportFacultyDTO;
import com.tps.dto.MissingReportStudentDTO;
import com.tps.pojo.MissingReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MissingReportConverter {
    @Autowired
    MissionConverter missionConverter;

    public MissingReportFacultyDTO toDTOObject(Object[] o) {
        MissingReportFacultyDTO dto = new MissingReportFacultyDTO();
        dto.setLastName(o[0].toString());
        dto.setFirstName(o[1].toString());
        dto.setStudentId(o[2].toString());
        dto.setMissionId(o[3].toString());
        dto.setCreatedDate(o[4].toString());
        dto.setActive((Boolean) o[5]);

        return dto;
    }

    public MissingReportDTO toDTO(MissingReport missingreport) {
        MissingReportDTO missingReportDTO = new MissingReportDTO();
        missingReportDTO.setRegisterDate(missingreport.getCreatedDate().toString());
        missingReportDTO.setMission(missionConverter.toDTO(missingreport.getMission()));
        missingReportDTO.setDescription(missingreport.getDescription());

        return missingReportDTO;
    }

    public MissingReportStudentDTO toMissingReportStudentDTO(Object[] o) {
        MissingReportStudentDTO missingReportStudentDTO = new MissingReportStudentDTO();
        missingReportStudentDTO.setPointGroupName(o[0].toString());
        missingReportStudentDTO.setActivityName(o[1].toString());
        missingReportStudentDTO.setMissionId(o[2].toString());
        missingReportStudentDTO.setMissionName(o[3].toString());
        missingReportStudentDTO.setMissionPoint((Integer) o[4]);
        missingReportStudentDTO.setDescription(o[5].toString());
        missingReportStudentDTO.setStatus(o[6].toString());

        return missingReportStudentDTO;
    }
}
