package com.tps.repositories;

import java.util.List;
import java.util.Map;

public interface StatsRepository {
    List<Object[]> statsTrainingPointByFaculty(Map<String, String> params);
    List<Object[]> statsTrainingPointByRank(Map<String, String> params);
    List<Object[]> statsTrainingPoint(Map<String, String> params);
}
