package com.tps.services;

import com.tps.pojo.MissingReport;

import java.util.List;
import java.util.Map;

public interface MissingReportService {
    MissingReport getMissingByStudentMission(int studentId, int missionId);
    void updateMissingReport (MissingReport missingreport);
    List<Object[]> getMissionReportByFaculty(int facultyId);
    MissingReport addMissingReport(int studentId, int missionId, Map<String, String> params);
}
