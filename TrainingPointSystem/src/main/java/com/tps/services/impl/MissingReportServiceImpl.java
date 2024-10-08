package com.tps.services.impl;

import com.tps.pojo.MissingReport;
import com.tps.repositories.MissingReportRepository;
import com.tps.services.MissingReportService;
import com.tps.services.MissionService;
import com.tps.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Service
public class MissingReportServiceImpl implements MissingReportService {
    @Autowired
    MissingReportRepository missingReportRepository;

    @Autowired
    MissionService missionService;

    @Autowired
    UserService userService;

    @Override
    public MissingReport getMissingById(int missingReportId) {
        return this.missingReportRepository.getMissingById(missingReportId);
    }

    @Override
    public MissingReport getMissingByStudentMission(int studentId, int missionId) {
        return this.missingReportRepository.getMissingByStudentMission(studentId, missionId);
    }

    @Override
    public void updateMissingReport(MissingReport missingReport) {
        this.missingReportRepository.updateMissingReport(missingReport);

    }

    @Override
    public List<Object[]> getMissionReportByFaculty(int facultyId) {
        return this.missingReportRepository.getMissionReportByFaculty(facultyId);
    }

    @Override
    public MissingReport addMissingReport(int studentId, int missionId, Map<String, String> params) {
        MissingReport missingReport = new MissingReport();
        missingReport.setMission(missionService.getMissionById(missionId));
        missingReport.setStudent(userService.getUserById(studentId).getStudent());
        missingReport.setDescription(params.get("description"));

        return this.missingReportRepository.addMissingReport(missingReport);
    }

    @Override
    public void uploadMissingImages(List<MultipartFile> files, int missing_id) {
        this.missingReportRepository.uploadMissingImages(files, missing_id);
    }

    @Override
    public List<Object[]> getMissingReportByStudentId(int studentId, int periodId) {
        return this.missingReportRepository.getMissingReportByStudentId(studentId, periodId);
    }
}
