package com.tps.services;

import com.tps.pojo.RegisterMission;
import org.springframework.web.multipart.MultipartFile;

public interface RegisterMissionService {
    void addRegisterMission(RegisterMission registermission);
    void updateRegisterMission(MultipartFile file, int activityId);
}
