package com.tps.repositories;

import com.tps.pojo.Student;

public interface StudentRepository {
    Student findStudentByStudentId(String studentId);
}
