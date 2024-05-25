package com.tps.repositories;

import com.tps.pojo.Registermission;

public interface RegisterMissionRepository {
    Registermission getRegisterByStudentMission(int studentId, int missionId);
    void updateRegistermission (Registermission registermission);

    Registermission addRegisterMission(Registermission registermission);
}
