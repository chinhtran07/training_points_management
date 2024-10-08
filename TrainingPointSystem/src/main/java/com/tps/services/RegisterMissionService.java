package com.tps.services;

import com.tps.pojo.RegisterMission;
import org.springframework.web.multipart.MultipartFile;

public interface RegisterMissionService {
    RegisterMission getRegisterByStudentMission(int studentId, int missionId);

    void updateRegistermission(RegisterMission registermission);

    RegisterMission addRegisterMission(int studentId, int missionId);

    void updateRegisterMission(MultipartFile file, String activityId);
}
