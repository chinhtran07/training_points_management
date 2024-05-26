package com.tps.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;

@Getter
@Setter
public class RankTotalPointsDTO {
    private BigInteger excellent;
    private BigInteger good;
    private BigInteger fair;
    private BigInteger average;
    private BigInteger weak;
    private BigInteger poor;
}
