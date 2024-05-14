package com.tps.repositories;

import java.util.List;
import java.util.Map;

public interface StatsRepository {
    List<Object[]> statsTrainingPoint(Map<String, String> params);
}
