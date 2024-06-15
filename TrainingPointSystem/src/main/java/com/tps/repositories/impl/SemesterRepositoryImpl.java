package com.tps.repositories.impl;

import com.tps.pojo.Semester;
import com.tps.repositories.SemesterRepository;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class SemesterRepositoryImpl implements SemesterRepository {

    @Autowired
    private LocalSessionFactoryBean sessionFactory;

    @Override
    public List<Semester> getAllSemesters() {
        Session session = this.sessionFactory.getObject().getCurrentSession();

        return session.createQuery("from Semester").getResultList();
    }
}
