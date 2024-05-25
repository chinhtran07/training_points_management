package com.tps.services;

import com.tps.pojo.Missingreport;

import java.util.Map;

public interface MissingReportService {
    Missingreport getMissingByStudentMission(int studentId, int missionId);
    void updateMissingreport (Missingreport missingreport);
    Missingreport addMissingreport(int studentId, int missionId, Map<String, String> params);
}
