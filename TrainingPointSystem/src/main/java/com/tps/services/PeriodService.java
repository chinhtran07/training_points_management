package com.tps.services;

import com.tps.pojo.Period;

import java.util.List;

public interface PeriodService {
    List<String> getYears();
    Period getPeriod(String year, int semesterId);
    List<Period> getAllPeriods(String year);
}
