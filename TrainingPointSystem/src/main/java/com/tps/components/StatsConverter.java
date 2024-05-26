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
        dto.setExcellent((BigInteger) o[0]);
        dto.setGood((BigInteger) o[1]);
        dto.setFair((BigInteger) o[2]);
        dto.setAverage((BigInteger) o[3]);
        dto.setWeak((BigInteger) o[4]);
        dto.setPoor((BigInteger) o[5]);

        return dto;
    }

    public TotalPointsDTO toTotalPointsDTO(Object[] o) {
        TotalPointsDTO dto = new TotalPointsDTO();
        dto.setFacultyName(o[0].toString());
        dto.setExcellent((Integer) o[1]);
        dto.setGood((Integer) o[2]);
        dto.setFair((Integer) o[3]);
        dto.setAverage((Integer) o[4]);
        dto.setWeak((Integer) o[5]);
        dto.setPoor((Integer) o[6]);
        dto.setTotalStudents((Integer) o[7]);
        dto.setAvgTotalPoints((Integer) o[8]);

        return dto;
    }

}
