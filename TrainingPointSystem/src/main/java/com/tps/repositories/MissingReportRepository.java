package com.tps.repositories;

import com.tps.pojo.MissingReport;
import com.tps.pojo.RegisterMission;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface MissingReportRepository {
    MissingReport getMissingByStudentMission(int studentId, int missionId);
    void updateMissingReport (MissingReport missingreport);
    List<Object[]> getMissionReportByFaculty(int facultyId);
    MissingReport addMissingReport(MissingReport missingreport);
    void uploadMissingImages(List<MultipartFile> files, int missing_id);
    List<Object[]> getMissingReportByStudentId(int studentId, int periodId);
}
