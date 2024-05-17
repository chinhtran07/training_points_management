package com.tps.services.impl;

import com.tps.dto.StudentTotalPointsDTO;
import com.tps.repositories.StatsRepository;
import com.tps.services.StatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Tuple;
import java.util.List;
import java.util.Map;

@Service
public class StatsServiceImpl implements StatsService {

    @Autowired
    private StatsRepository statsRepository;


    @Override
    public List<Map<String,Object>> statsTrainingPoint(Map<String, String> params) {
        return this.statsRepository.statsTrainingPoint(params);
    }
}
