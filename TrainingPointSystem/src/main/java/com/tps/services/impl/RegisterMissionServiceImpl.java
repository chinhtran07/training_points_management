package com.tps.services.impl;

import com.tps.pojo.Registermission;
import com.tps.pojo.RegistermissionId;
import com.tps.repositories.MissionRepository;
import com.tps.repositories.RegisterMissionRepository;
import com.tps.repositories.UserRepository;
import com.tps.services.RegisterMissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class RegisterMissionServiceImpl implements RegisterMissionService {
    @Autowired
    RegisterMissionRepository registerMissionRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    MissionRepository missionRepository;


    @Override
    public Registermission getRegisterByStudentMission(int studentId, int missionId) {
        return this.registerMissionRepository.getRegisterByStudentMission(studentId, missionId);
    }

    @Override
    public void updateRegistermission(Registermission registermission) {
        this.registerMissionRepository.updateRegistermission(registermission);
    }

    @Override
    public Registermission addRegisterMission(int studentId, int missionId) {
        Registermission registermission = new Registermission();
        registermission.setMission(missionRepository.getMissionById(missionId));
        registermission.setStudent(userRepository.getUserById(studentId).getStudent());

        return this.registerMissionRepository.addRegisterMission(registermission);
    }

    @Override
    public Registermission registerMission(int studentId, int missionId) {
//        Registermission registermission =  this.getRegisterByStudentMission(studentId, missionId);
//        if(registermission == null) {
//
//        }
        return null;
    }
}
