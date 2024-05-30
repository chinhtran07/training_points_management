package com.tps.services.impl;

import com.tps.pojo.Class;
import com.tps.pojo.Student;
import com.tps.repositories.StudentRepository;
import com.tps.services.ClassService;
import com.tps.services.FacultyService;
import com.tps.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    ClassService classService;

    @Autowired
    FacultyService facultyService;

    @Override
    public Student getStudentByStudentId(String studentId) {
        return this.studentRepository.findStudentByStudentId(studentId);
    }

    @Override
    public List<Object> getResultOfTrainingPointById(int id) {
        return this.studentRepository.getResultOfTrainingPointById(id);
    }

    @Override
    public void addStudent(Map<String, String> params) {
        Student student = new Student();
        student.setClassField(classService.getClassById(Integer.parseInt(params.get("class"))));
        student.setFaculty(facultyService.getFacultyById(Integer.parseInt(params.get("faculty"))));
    }
}
