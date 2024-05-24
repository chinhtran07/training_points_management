package com.tps.components;


import com.tps.dto.ClassTotalPointsDTO;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class StatsConverter {

    public ClassTotalPointsDTO toDTO(Object[] o) {
        ClassTotalPointsDTO dto = new ClassTotalPointsDTO();
        dto.setName((o[0].toString()));
        dto.setTotalStudents(o[1].toString());
        dto.setAvgTotalPoints(o[2].toString());

        return dto;
    }
}
