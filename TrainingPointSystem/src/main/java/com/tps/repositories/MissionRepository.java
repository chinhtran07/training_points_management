package com.tps.repositories;

import com.tps.pojo.Activity;
import com.tps.pojo.Mission;

public interface MissionRepository {
    void addOrUpdateMission(Mission mission);
    boolean checkMissionBelongToActivity(int activityId, int missionId);
}
