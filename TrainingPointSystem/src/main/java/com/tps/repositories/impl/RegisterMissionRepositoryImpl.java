package com.tps.repositories.impl;

import com.tps.pojo.Registermission;
import com.tps.repositories.RegisterMissionRepository;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
class RegisterMissionRepositoryImpl implements RegisterMissionRepository {
    @Autowired
    LocalSessionFactoryBean factoryBean;

    @Override
    public Registermission getRegisterByStudentMission(int studentId, int missionId) {
        Session session = factoryBean.getObject().getCurrentSession();
        Query query = session.createQuery("FROM Registermission WHERE student.id=:studentId AND mission.id=:missionId");
        query.setParameter("studentId", studentId);
        query.setParameter("missionId", missionId);
        return (Registermission) query.uniqueResult();
    }

    @Override
    public void updateRegistermission(Registermission registermission) {
        Session session = factoryBean.getObject().getCurrentSession();
        session.update(registermission);
    }

    @Override
    public Registermission addRegisterMission(Registermission registermission) {
        Session session = factoryBean.getObject().getCurrentSession();
        session.save(registermission);
        return registermission;
    }
}
