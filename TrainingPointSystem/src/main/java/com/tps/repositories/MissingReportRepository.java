package com.tps.repositories;

import com.tps.pojo.MissingReport;
import com.tps.pojo.RegisterMission;

import java.util.List;

public interface MissingReportRepository {
    MissingReport getMissingByStudentMission(int studentId, int missionId);
    void updateMissingreport (MissingReport missingreport);
    List<Object[]> getMissionReportByFaculty(int facultyId);
    MissingReport addMissingreport(MissingReport missingreport);
}
