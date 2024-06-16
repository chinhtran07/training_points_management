package com.tps.services.impl;

import com.tps.pojo.Period;
import com.tps.repositories.PeriodRepository;
import com.tps.services.PeriodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PeriodServiceImpl implements PeriodService {

    @Autowired
    private PeriodRepository periodRepository;

    @Override
    public List<String> getYears() {
        return this.periodRepository.getYears();
    }

    @Override
    public Period getPeriod(String year, int semesterId) {
        return this.periodRepository.getPeriod(year, semesterId);
    }

    @Override
    public List<Period> getAllPeriods(String year) {
        return this.periodRepository.getAllPeriods(year);
    }
}
