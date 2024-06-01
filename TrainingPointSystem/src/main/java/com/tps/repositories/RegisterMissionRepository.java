package com.tps.repositories;

import com.tps.pojo.RegisterMission;

import java.util.List;

public interface RegisterMissionRepository {
    RegisterMission getRegisterByStudentMission(int studentId, int missionId);
    void updateRegistermission (RegisterMission registermission);
    RegisterMission addRegisterMission(RegisterMission registermission);
    void updateStatus(List<RegisterMission> registerMissionList);
}
