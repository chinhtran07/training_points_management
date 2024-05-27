package com.tps.components;


import com.tps.dto.ClassTotalPointsDTO;
import com.tps.dto.RankTotalPointsDTO;
import com.tps.dto.TotalPointsDTO;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.BigInteger;

@Component
public class StatsConverter {

    public ClassTotalPointsDTO toClassTotalPointDTO(Object[] o) {
        ClassTotalPointsDTO dto = new ClassTotalPointsDTO();
        dto.setName((o[0].toString()));
        dto.setTotalStudents(o[1].toString());
        dto.setAvgTotalPoints(o[2].toString());

        return dto;
    }

    public RankTotalPointsDTO toRankTotalPointsDTO(Object[] o) {
        RankTotalPointsDTO dto = new RankTotalPointsDTO();
        dto.setExcellent((Long) o[0]);
        dto.setGood((Long) o[1]);
        dto.setFair((Long) o[2]);
        dto.setAverage((Long) o[3]);
        dto.setWeak((Long) o[4]);
        dto.setPoor((Long) o[5]);

        return dto;
    }

    public TotalPointsDTO toTotalPointsDTO(Object[] o) {
        TotalPointsDTO dto = new TotalPointsDTO();
        dto.setFacultyName(o[0].toString());
        dto.setExcellent((Long) o[1]);
        dto.setGood((Long) o[2]);
        dto.setFair((Long) o[3]);
        dto.setAverage((Long) o[4]);
        dto.setWeak((Long) o[5]);
        dto.setPoor((Long) o[6]);
        dto.setTotalStudents((Long) o[7]);
        dto.setAvgTotalPoints((Double) o[8]);

        return dto;
    }

}
