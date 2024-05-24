package com.tps.services.impl;

import com.tps.repositories.StatsRepository;
import com.tps.services.StatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Tuple;
import java.util.List;

@Service
public class StatsServiceImpl implements StatsService {

    @Autowired
    private StatsRepository statsRepository;


    @Override
    public List<Object[]> statsTrainingPointByFaculty(String facultyId) {
        return this.statsRepository.statsTrainingPointByFaculty(facultyId);
    }

    @Override
    public List<Object[]> statsTrainingPointByRank() {
        return this.statsRepository.statsTrainingPointByRank();
    }

    @Override
    public List<Object[]> statsTrainingPoints() {
        return this.statsRepository.statsTrainingPoint();
    }
}
