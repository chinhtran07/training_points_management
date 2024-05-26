package com.tps.services;

import com.tps.pojo.Mission;

public interface MissionService {
    void addOrUpdateMission(Mission mission, int activityId);
    boolean checkMissionBelongToActivity(int missionId, int activityId);
}
