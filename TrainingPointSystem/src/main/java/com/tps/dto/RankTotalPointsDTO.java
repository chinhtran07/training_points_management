package com.tps.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;

@Getter
@Setter
public class RankTotalPointsDTO {
    private Long excellent;
    private Long good;
    private Long fair;
    private Long average;
    private Long weak;
    private Long poor;
}
