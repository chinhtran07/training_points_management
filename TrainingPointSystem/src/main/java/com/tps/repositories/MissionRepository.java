package com.tps.repositories;

import com.tps.pojo.Mission;

import java.util.List;
import java.util.Map;

public interface MissionRepository {
    List<Mission> getMission(Map<String, String> params);

    List<Object[]> getUserMission(int userId, Map<String, String> params);

    Mission getMissionById(int id);

    Mission addMission(Mission mission);

    Mission updateMission(Mission mission);

    void deleteMission(int id);



}
