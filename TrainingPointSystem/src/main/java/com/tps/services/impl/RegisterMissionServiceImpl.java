package com.tps.services.impl;

import com.tps.pojo.RegisterMission;
import com.tps.repositories.MissionRepository;
import com.tps.repositories.RegisterMissionRepository;
import com.tps.repositories.UserRepository;
import com.tps.services.RegisterMissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import com.tps.pojo.RegisterMission;
import com.tps.pojo.Student;
import com.tps.repositories.MissionRepository;
import com.tps.repositories.RegisterMissionRepository;
import com.tps.repositories.StudentRepository;
import com.tps.services.RegisterMissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class RegisterMissionServiceImpl implements RegisterMissionService {
    @Autowired
    RegisterMissionRepository registerMissionRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    MissionRepository missionRepository;


    @Override
    public RegisterMission getRegisterByStudentMission(int studentId, int missionId) {
        return this.registerMissionRepository.getRegisterByStudentMission(studentId, missionId);
    }

    @Override
    public void updateRegistermission(RegisterMission registermission) {
        this.registerMissionRepository.updateRegistermission(registermission);
    }

    @Override
    public RegisterMission addRegisterMission(int studentId, int missionId) {
        RegisterMission registermission = new RegisterMission();
        registermission.setMission(missionRepository.getMissionById(missionId));
        registermission.setStudent(userRepository.getUserById(studentId).getStudent());

        return this.registerMissionRepository.addRegisterMission(registermission);
    }


    @Override
    public void updateRegisterMission(MultipartFile file, String activityId) {
        try (CSVReader csvReader = new CSVReader(new InputStreamReader(file.getInputStream()))) {
            List<RegisterMission> updateList = new ArrayList<>();
            String[] nextLine;
            while ((nextLine = csvReader.readNext()) != null) {
                String studentId = nextLine[0].substring(1);
                String missionId = nextLine[1];
                boolean isCompleted = nextLine[2].equals("1");
                if (this.missionRepository.checkMissionBelongToActivity(activityId, missionId)) {
                    Student student = this.studentRepository.findStudentByStudentId(studentId);
                    RegisterMission registermission = this.registerMissionRepository.getRegisterByStudentMission(student.getId(), Integer.parseInt(missionId));
                    registermission.setIsCompleted(isCompleted);
                    updateList.add(registermission);
                }
            }
            this.registerMissionRepository.updateStatus(updateList);
        } catch (IOException | CsvValidationException e) {
            throw new RuntimeException(e);
        }
    }
}
