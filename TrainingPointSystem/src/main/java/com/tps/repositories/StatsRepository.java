package com.tps.repositories;

import com.tps.dto.StudentTotalPointsDTO;

import javax.persistence.Tuple;
import java.util.List;
import java.util.Map;

public interface StatsRepository {
    List<Map<String, Object>> statsTrainingPoint(Map<String, String> params);
}
