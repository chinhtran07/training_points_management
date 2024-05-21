package com.tps.components;


import com.tps.dto.StudentTotalPointsDTO;
import com.tps.pojo.Student;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class StatsConverter {
    public StudentTotalPointsDTO toDTO(Object[] o) {
        StudentTotalPointsDTO studentTotalPointsDTO = new StudentTotalPointsDTO();
        studentTotalPointsDTO.setId((Integer)o[0]);
        studentTotalPointsDTO.setStudentId(o[1].toString());
        studentTotalPointsDTO.setFirstName(o[2].toString());
        studentTotalPointsDTO.setLastName(o[3].toString());
        studentTotalPointsDTO.setClassName(o[4].toString());
        studentTotalPointsDTO.setFacultyName(o[5].toString());
        studentTotalPointsDTO.setTotalPoints((BigDecimal) o[6]);

        return studentTotalPointsDTO;
    }
}
