package com.tps.services.impl;

import com.tps.pojo.MissingReport;
import com.tps.repositories.MissingReportRepository;
import com.tps.repositories.MissionRepository;
import com.tps.repositories.UserRepository;
import com.tps.services.MissingReportService;
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
    MissionRepository missionRepository;

    @Autowired
    UserRepository userRepository;

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
        missingReport.setMission(missionRepository.getMissionById(missionId));
        missingReport.setStudent(userRepository.getUserById(studentId).getStudent());
        missingReport.setDescription(params.get("description"));

        return this.missingReportRepository.addMissingReport(missingReport);    }

    @Override
    public void uploadMissingImages(List<MultipartFile> files, int missing_id) {
        this.missingReportRepository.uploadMissingImages(files, missing_id);
    }
}
