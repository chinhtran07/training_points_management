package com.tps.services;

import com.tps.pojo.Student;

import java.util.List;
import java.util.Map;

public interface StudentService {
    Student findStudentByStudentId(String studentId);

    List<Object> getResultOfTrainingPointById(int id);

    void addStudent(Map<String, String> params);
}
