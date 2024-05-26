package com.tps.services;

import com.tps.dto.MissionCreateDTO;
import com.tps.dto.MissionDTO;
import com.tps.dto.RegisterMissionDTO;
import com.tps.pojo.Mission;

import java.util.List;
import java.util.Map;

public interface MissionService {
    List<Mission> getMission(Map<String, String> params);

    List<RegisterMissionDTO> getUserMission(int userId, Map<String, String> params);

    Mission getMissionById(int id);

    MissionDTO addMission(MissionDTO mission);

    MissionDTO updateMission(int missionId, MissionCreateDTO missionDTO);

    void deleteMission(int id);

    void addOrUpdateMission(Mission mission, int activityId);
    boolean checkMissionBelongToActivity(int missionId, int activityId);
}
