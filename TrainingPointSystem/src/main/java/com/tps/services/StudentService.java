package com.tps.services;

import com.tps.pojo.Student;
import com.tps.pojo.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface StudentService {
    Student findStudentByStudentId(String studentId);

    List<Object> getResultOfTrainingPointById(int id);

    User addStudent(Map<String, String> params, MultipartFile[] files);
}
