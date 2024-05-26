package com.tps.services.impl;

import com.tps.pojo.MissingReport;
import com.tps.pojo.RegisterMission;
import com.tps.repositories.MissingReportRepository;
import com.tps.repositories.MissionRepository;
import com.tps.repositories.UserRepository;
import com.tps.services.MissingReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@Service
public class MissingReportServiceImpl implements MissingReportService {
    @Autowired
    MissingReportRepository MissingReportRepository;

    @Autowired
    MissionRepository missionRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public MissingReport getMissingByStudentMission(int studentId, int missionId) {
        return this.MissingReportRepository.getMissingByStudentMission(studentId, missionId);
    }

    @Override
    public void updateMissingReport(MissingReport MissingReport) {
        this.MissingReportRepository.updateMissingReport(MissingReport);
    }

    @Override
    public MissingReport addMissingReport(int studentId, int missionId, Map<String, String> params) {
        MissingReport MissingReport = new MissingReport();
        MissingReport.setMission(missionRepository.getMissionById(missionId));
        MissingReport.setStudent(userRepository.getUserById(studentId).getStudent());
        MissingReport.setDescription(params.get("description"));

        return this.MissingReportRepository.addMissingReport(MissingReport);
    }

    @Override
    public List<Object[]> getMissionReportByFaculty(int facultyId) {
        return this.repository.getMissionReportByFaculty(facultyId);
    }
}
