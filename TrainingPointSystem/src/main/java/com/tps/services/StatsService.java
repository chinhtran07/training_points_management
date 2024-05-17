package com.tps.services;

import com.tps.dto.StudentTotalPointsDTO;

import javax.persistence.Tuple;
import java.util.List;
import java.util.Map;

public interface StatsService {
    List<Map<String, Object>> statsTrainingPoint(Map<String, String> params);
}
