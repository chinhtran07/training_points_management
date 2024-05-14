package com.tps.services;

import java.util.List;
import java.util.Map;

public interface StatsService {
    List<Object[]> statsTrainingPoint(Map<String, String> params);
}
