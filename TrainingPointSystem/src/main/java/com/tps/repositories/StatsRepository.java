package com.tps.repositories;

import java.util.List;

public interface StatsRepository {
    List<Object[]> statsTrainingPointByFaculty(String facultyId);
    List<Object[]> statsTrainingPointByRank();
    List<Object[]> statsTrainingPoint();
}
