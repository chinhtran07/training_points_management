package com.tps.services;

import com.tps.pojo.Registermission;
import com.tps.repositories.RegisterMissionRepository;

public interface RegisterMissionService {
    Registermission getRegisterByStudentMission(int studentId, int missionId);
    void updateRegistermission (Registermission registermission);

    Registermission addRegisterMission(int studentId, int missionId);

    Registermission registerMission(int studentId, int missionId);
}
