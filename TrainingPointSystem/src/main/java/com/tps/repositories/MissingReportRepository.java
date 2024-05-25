package com.tps.repositories;

import com.tps.pojo.Missingreport;
import com.tps.pojo.Registermission;

public interface MissingReportRepository {
    Missingreport getMissingByStudentMission(int studentId, int missionId);
    void updateMissingreport (Missingreport missingreport);
    Missingreport addMissingreport(Missingreport missingreport);
}
