package com.tps.dto;

import lombok.Data;

@Data
public class PeriodDTO {
    private Integer id;
    private Integer semester;
    private String year;

    public PeriodDTO(Integer id, Integer semester, String year) {
        this.id = id;
        this.semester = semester;
        this.year = year;
    }
}
