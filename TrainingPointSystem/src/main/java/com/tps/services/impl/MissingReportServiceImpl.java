package com.tps.services.impl;

import com.tps.pojo.Missingreport;
import com.tps.pojo.Registermission;
import com.tps.repositories.MissingReportRepository;
import com.tps.repositories.MissionRepository;
import com.tps.repositories.UserRepository;
import com.tps.services.MissingReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
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
    public Missingreport getMissingByStudentMission(int studentId, int missionId) {
        return this.missingReportRepository.getMissingByStudentMission(studentId, missionId);
    }

    @Override
    public void updateMissingreport(Missingreport missingreport) {
        this.missingReportRepository.updateMissingreport(missingreport);
    }

    @Override
    public Missingreport addMissingreport(int studentId, int missionId, Map<String, String> params) {
        Missingreport missingreport = new Missingreport();
        missingreport.setMission(missionRepository.getMissionById(missionId));
        missingreport.setStudent(userRepository.getUserById(studentId).getStudent());
        missingreport.setDescription(params.get("description"));

        return this.missingReportRepository.addMissingreport(missingreport);
    }
}
