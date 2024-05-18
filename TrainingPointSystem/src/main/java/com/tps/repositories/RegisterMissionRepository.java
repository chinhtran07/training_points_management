package com.tps.repositories;

import com.tps.pojo.Mission;
import com.tps.pojo.Registermission;
import com.tps.pojo.RegistermissionId;

import java.util.Optional;

public interface RegisterMissionRepository {
    void addOrUpdateStatus(Registermission mission);
    Registermission findById(int studentId, int missionId);
}
