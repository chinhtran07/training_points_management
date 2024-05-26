package com.tps.components;

import com.tps.dto.MissingReportDTO;
import com.tps.pojo.MissingReport;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;

@Component
public class MissingReportConverter {
    public MissingReportDTO toDTO(Object[] o) {
        MissingReportDTO dto = new MissingReportDTO();
        dto.setLastName(o[0].toString());
        dto.setFirstName(o[1].toString());
        dto.setStudentId(o[2].toString());
        dto.setMissionId(o[3].toString());
        dto.setCreatedDate(o[4].toString());
        dto.setIsActive((Boolean) o[5]);

        return dto;
    }
}