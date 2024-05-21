package com.tps.dto;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.SqlResultSetMapping;
import java.math.BigDecimal;

@Getter
@Setter
public class StudentTotalPointsDTO {
    private int id;
    private String studentId;
    private String firstName;
    private String lastName;
    private String className;
    private String facultyName;
    private BigDecimal totalPoints;
}
