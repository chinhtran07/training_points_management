package com.tps.components;


import com.tps.dto.ClassTotalPointsDTO;
import com.tps.dto.RankTotalPointsDTO;
import com.tps.dto.TotalPointsDTO;
import org.springframework.stereotype.Component;

@Component
public class StatsConverter {

    public ClassTotalPointsDTO toClassTotalPointDTO(Object[] o) {
        ClassTotalPointsDTO dto = new ClassTotalPointsDTO();
        dto.setName((o[0].toString()));
        dto.setTotalStudents((Long) o[1]);
        dto.setAvgTotalPoints(o[2] != null ? (Double) o[2] : 0.0);

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
        dto.setTotalStudents((Long) o[1]);
        dto.setAvgTotalPoints((Double) o[2]);

        return dto;
    }

}
