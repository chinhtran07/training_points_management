package com.tps.services.impl;

import com.tps.pojo.Student;
import com.tps.repositories.StudentRepository;
import com.tps.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public Student getStudentByStudentId(String studentId) {
        return this.studentRepository.findStudentByStudentId(studentId);
    }

    @Override
    public List<Object> getResultOfTrainingPointById(int id) {
        return this.studentRepository.getResultOfTrainingPointById(id);
    }
}
