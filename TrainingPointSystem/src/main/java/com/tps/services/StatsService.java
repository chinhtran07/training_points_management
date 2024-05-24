package com.tps.services;

import javax.persistence.Tuple;
import java.util.List;

public interface StatsService {
    List<Object[]> statsTrainingPointByFaculty(String facultyId);
    List<Object[]> statsTrainingPointByRank();
    List<Object[]> statsTrainingPoints();
}
