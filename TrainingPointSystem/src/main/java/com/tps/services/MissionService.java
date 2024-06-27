package com.tps.services;

import com.tps.dto.MissionCreateDTO;
import com.tps.dto.MissionDTO;
import com.tps.dto.RegisterMissionDTO;
import com.tps.pojo.Mission;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface MissionService {
    List<Mission> getMission(Map<String, String> params);

    List<RegisterMissionDTO> getUserMission(int userId, Map<String, String> params);

    Mission getMissionById(int id);

    Mission addMission(Mission mission, int activityId);

    MissionDTO updateMission(int missionId, MissionCreateDTO missionDTO);

    void deleteMission(int id);

    Boolean checkMissionBelongToActivity(String activityId, String missionId);

    List<Mission> getExpiredMissions(LocalDate currentTime);

    Mission updateMission(Mission mission);
}
 