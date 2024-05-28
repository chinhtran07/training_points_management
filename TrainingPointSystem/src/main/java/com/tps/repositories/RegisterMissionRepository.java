package com.tps.repositories;

import com.tps.pojo.RegisterMission;

public interface RegisterMissionRepository {
    RegisterMission getRegisterByStudentMission(int studentId, int missionId);
    void updateRegistermission (RegisterMission registermission);
    RegisterMission addRegisterMission(RegisterMission registermission);
    void addOrUpdateStatus(RegisterMission mission);
}
