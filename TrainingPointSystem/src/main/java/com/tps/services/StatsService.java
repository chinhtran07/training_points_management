package com.tps.services;

import java.util.List;
import java.util.Map;

public interface StatsService {
    List<Object[]> statsTrainingPointByFaculty(Map<String, String> params);

    List<Object[]> statsTrainingPointByRank(Map<String, String> params);

    List<Object[]> statsTrainingPoints(Map<String, String> params);
}
