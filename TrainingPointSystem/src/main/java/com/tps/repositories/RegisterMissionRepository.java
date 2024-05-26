package com.tps.repositories;

import com.tps.pojo.RegisterMission;

public interface RegisterMissionRepository {
    void addOrUpdateStatus(RegisterMission mission);
    RegisterMission findById(int studentId, int missionId);
}
