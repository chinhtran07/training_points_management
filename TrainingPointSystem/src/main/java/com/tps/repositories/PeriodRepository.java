package com.tps.repositories;

import com.tps.pojo.Period;

import java.util.List;

public interface PeriodRepository {
    List<String> getYears();
    Period getPeriod(String year, int semesterId);
}
