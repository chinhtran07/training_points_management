package com.tps.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PointGroupDTO {
    private int id;
    private String name;
    private String content;
    private int maxPoint;
}
