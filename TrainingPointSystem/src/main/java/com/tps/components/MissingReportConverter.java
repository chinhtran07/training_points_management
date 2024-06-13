package com.tps.components;

import com.tps.dto.MissingReportDTO;
import com.tps.dto.MissingReportFacultyDTO;
import com.tps.pojo.MissingReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class MissingReportConverter {
    @Autowired
    MissionConverter missionConverter;

    @Autowired
    UserConverter userConverter;

    @Autowired
    ImageConverter imageConverter;


    public MissingReportFacultyDTO toDTOObject(Object[] o) {
        MissingReportFacultyDTO dto = new MissingReportFacultyDTO();
        dto.setLastName(o[0].toString());
        dto.setFirstName(o[1].toString());
        dto.setStudentId(o[2].toString());
        dto.setMissionName(o[3].toString());
        dto.setCreatedDate(o[4].toString());
        dto.setActive((Boolean) o[5]);
        dto.setStatus(o[6].toString());
        dto.setId((Integer) o[7]);
        dto.setActivityName(o[8].toString());

        return dto;
    }

    public MissingReportDTO toDTO(MissingReport missingreport) {
        MissingReportDTO missingReportDTO = new MissingReportDTO();
        missingReportDTO.setRegisterDate(missingreport.getCreatedDate().toString());
        missingReportDTO.setMission(missionConverter.toDTO(missingreport.getMission()));
        missingReportDTO.setDescription(missingreport.getDescription());
        missingReportDTO.setStudent(userConverter.toStudentDTO(missingreport.getStudent()));
        missingReportDTO.setStatus(missingreport.getStatus().toString());
        missingReportDTO.setImages(missingreport.getImages().stream().map(image -> imageConverter.toDTO(image)).collect(Collectors.toList()));
        return missingReportDTO;
    }
}
