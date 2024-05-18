package com.tps.services.impl;

import com.opencsv.CSVReader;

import com.opencsv.exceptions.CsvValidationException;
import com.tps.pojo.Registermission;
import com.tps.pojo.RegistermissionId;
import com.tps.pojo.Student;
import com.tps.repositories.RegisterMissionRepository;
import com.tps.repositories.StudentRepository;
import com.tps.services.RegisterMissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.time.Instant;

@Service
public class RegisterMissionServiceImpl implements RegisterMissionService {

    @Autowired
    private RegisterMissionRepository registerMissionRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public void addRegisterMission(Registermission registermission) {
        this.registerMissionRepository.addOrUpdateStatus(registermission);
    }

    @Override
    public void updateRegisterMission(MultipartFile file) {
        try (CSVReader csvReader = new CSVReader(new InputStreamReader(file.getInputStream()))) {
            String[] nextLine;
            while ((nextLine = csvReader.readNext()) != null) {
                String studentId = nextLine[0];
                int missionId = Integer.parseInt(nextLine[1]);
                boolean isCompleted = Boolean.parseBoolean(nextLine[2]);
                updateRegisterMissionEntry(studentId, missionId, isCompleted);
            }
        } catch (IOException | CsvValidationException e) {
            throw new RuntimeException(e);
        }
    }

    private void updateRegisterMissionEntry(String studentId, int missionId, boolean isCompleted) {
        try {
            Student student = this.studentRepository.findStudentByStudentId(studentId);
            Registermission registermission = this.registerMissionRepository.findById(student.getId(), missionId);
            registermission.setIsCompleted(isCompleted);
            registermission.setUpdatedDate(Instant.now());
            this.registerMissionRepository.addOrUpdateStatus(registermission);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid student id " + studentId);
        }
    }
}

