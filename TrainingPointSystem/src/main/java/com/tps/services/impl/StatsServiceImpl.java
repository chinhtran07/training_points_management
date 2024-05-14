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
    public List<Object[]> statsTrainingPoint(Map<String, String> params) {
        return this.statsRepository.statsTrainingPoint(params);
    }
}
