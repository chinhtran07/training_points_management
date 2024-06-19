package com.tps.services.impl;

import com.tps.repositories.StatsRepository;
import com.tps.services.StatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class StatsServiceImpl implements StatsService {

    @Autowired
    private StatsRepository statsRepository;


    @Override
    public List<Object[]> statsTrainingPointByFaculty(Map<String, String> params) {
        return this.statsRepository.statsTrainingPointByFaculty(params);
    }

    @Override
    public List<Object[]> statsTrainingPointByRank(Map<String, String> params) {
        return this.statsRepository.statsTrainingPointByRank(params);
    }

    @Override
    public List<Object[]> statsTrainingPoints(Map<String, String> params) {
        return this.statsRepository.statsTrainingPoint(params);
    }
}
