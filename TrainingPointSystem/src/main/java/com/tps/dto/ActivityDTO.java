package com.tps.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ActivityDTO {
    private int Id;
    private String Name;
    private int pointGroupId;
}
