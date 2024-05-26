package com.tps.services.impl;

import com.tps.pojo.Activity;
import com.tps.pojo.Mission;
import com.tps.repositories.ActivityRepository;
import com.tps.repositories.MissionRepository;
import com.tps.services.MissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MissionServiceImpl implements MissionService {

    @Autowired
    private MissionRepository missionRepository;

    @Autowired
    private ActivityRepository activityRepository;

    @Override
    public void addOrUpdateMission(Mission mission, int activityId) {
        Activity activity = this.activityRepository.getActivityById(activityId);
        mission.setActivity(activity);
        this.missionRepository.addOrUpdateMission(mission);
    }

    @Override
    public boolean checkMissionBelongToActivity(int missionId, int activityId) {
        return this.missionRepository.checkMissionBelongToActivity(missionId, activityId);
    }
}
