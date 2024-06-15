package com.tps.services;

import com.tps.pojo.MissingReport;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface MissingReportService {
    MissingReport getMissingById(int missingReportId);
    MissingReport getMissingByStudentMission(int studentId, int missionId);
    void updateMissingReport (MissingReport missingreport);
    List<Object[]> getMissionReportByFaculty(int facultyId);
    MissingReport addMissingReport(int studentId, int missionId, Map<String, String> params);
    void uploadMissingImages(List<MultipartFile> files, int missing_id);
    List<Object[]> getMissingReportByStudentId(int studentId, int periodId);
}
