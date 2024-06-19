package com.tps.services.impl;

import com.tps.dto.ActivityResultDTO;
import com.tps.dto.MissionResultDTO;
import com.tps.dto.StudentResultDTO;
import com.tps.pojo.Student;
import com.tps.repositories.StudentRepository;
import com.tps.services.ClassService;
import com.tps.services.FacultyService;
import com.tps.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ClassService classService;

    @Autowired
    private FacultyService facultyService;

    @Override
    public Student findStudentByStudentId(String studentId) {
        return this.studentRepository.findStudentByStudentId(studentId);
    }

    @Override
    public List<Object> getResultOfTrainingPointById(int id) {
        List<Object> results = this.studentRepository.getResultOfTrainingPointById(id);

        Map<Integer, StudentResultDTO> studentResultMap = new HashMap<>();

        for (Object result : results) {
            Object[] entry = (Object[]) result;

            int pointGroupId = (Integer) entry[0];
            String pointGroupName = (String) entry[1];
            String pointGroupContent = (String) entry[2];
            int pointGroupMaxPoint = (Integer) entry[3];

            int activityId = (Integer) entry[4];
            String activityName = (String) entry[5];
            int activityMaxPoint = (Integer) entry[6];

            int missionId = (Integer) entry[7];
            String missionName = (String) entry[8];
            int missionPoint = (Integer) entry[9];

            StudentResultDTO studentResult = studentResultMap.computeIfAbsent(pointGroupId, k -> new StudentResultDTO(pointGroupId, pointGroupName, pointGroupContent, pointGroupMaxPoint, new ArrayList<>()));

            MissionResultDTO missionResult = new MissionResultDTO(missionId, missionName, missionPoint);
            ActivityResultDTO activityResult = studentResult.getListActivity().stream()
                    .filter(a -> a.getActivityId() == activityId)
                    .findFirst()
                    .orElseGet(() -> {
                        ActivityResultDTO newActivity = new ActivityResultDTO(activityId, activityName, activityMaxPoint, new ArrayList<>());
                        studentResult.getListActivity().add(newActivity);
                        return newActivity;
                    });

            activityResult.getMissionResultDTOList().add(missionResult);
        }

        return new ArrayList<>(studentResultMap.values());
    }

    @Override
    public void addStudent(Map<String, String> params) {
        Student student = new Student();
        student.setClassField(classService.getClassById(Integer.parseInt(params.get("class"))));
        student.setFaculty(facultyService.getFacultyById(Integer.parseInt(params.get("faculty"))));
    }
}
