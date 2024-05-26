package com.tps.repositories;

import com.tps.pojo.MissingReport;
import com.tps.pojo.RegisterMission;

import java.util.List;

public interface MissingReportRepository {
    MissingReport getMissingByStudentMission(int studentId, int missionId);
    void updateMissingReport (MissingReport missingreport);
    List<Object[]> getMissionReportByFaculty(int facultyId);
    MissingReport addMissingReport(MissingReport missingreport);
}
