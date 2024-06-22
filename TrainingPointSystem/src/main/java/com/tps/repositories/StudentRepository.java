package com.tps.repositories;

import com.tps.pojo.Student;
import com.tps.pojo.User;

import java.util.List;

public interface StudentRepository {
    Student findStudentByStudentId(String studentId);

    List<Object> getResultOfTrainingPointById(int id);

    Student addStudent(Student student);
}
