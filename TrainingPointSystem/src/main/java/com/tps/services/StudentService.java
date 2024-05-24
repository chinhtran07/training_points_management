package com.tps.services;

import com.tps.pojo.Student;

import java.util.List;

public interface StudentService {
    Student getStudentByStudentId(String studentId);
    List<Object> getResultOfTrainingPointById(int id);
}
