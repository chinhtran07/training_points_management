package com.tps.services;

import com.tps.pojo.Registermission;
import org.springframework.web.multipart.MultipartFile;

public interface RegisterMissionService {
    void addRegisterMission(Registermission registermission);
    void updateRegisterMission(MultipartFile file, int activityId);
}
