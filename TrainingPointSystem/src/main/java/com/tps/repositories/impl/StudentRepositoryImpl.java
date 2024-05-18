package com.tps.repositories.impl;


import com.tps.pojo.Student;
import com.tps.repositories.StudentRepository;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class StudentRepositoryImpl implements StudentRepository {

    @Autowired
    private LocalSessionFactoryBean sessionFactory;

    @Override
    public Student findStudentByStudentId(String studentId) {
        Session session = this.sessionFactory.getObject().getCurrentSession();
        return (Student) session.get(Student.class, studentId);
    }
}
