package com.tps.repositories.impl;

import com.tps.pojo.RegisterMission;
import com.tps.repositories.RegisterMissionRepository;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
class RegisterMissionRepositoryImpl implements RegisterMissionRepository {
    @Autowired
    LocalSessionFactoryBean factoryBean;

    @Override
    public RegisterMission getRegisterByStudentMission(int studentId, int missionId) {
        Session session = factoryBean.getObject().getCurrentSession();
        Query query = session.createQuery("FROM RegisterMission WHERE student.id=:studentId AND mission.id=:missionId");
        query.setParameter("studentId", studentId);
        query.setParameter("missionId", missionId);
        return (RegisterMission) query.uniqueResult();
    }

    @Override
    public void updateRegistermission(RegisterMission registermission) {
        Session session = factoryBean.getObject().getCurrentSession();
        session.update(registermission);
    }

    @Override
    public RegisterMission addRegisterMission(RegisterMission registermission) {
        Session session = factoryBean.getObject().getCurrentSession();
        session.save(registermission);
        return registermission;
    }

    @Override
    public void updateStatus(List<RegisterMission> registerMissionList) {
        Session session = this.factoryBean.getObject().getCurrentSession();
        int batchSize = 100;
        for (int i = 0; i < registerMissionList.size(); i++) {
            session.update(registerMissionList.get(i));
            if (i % batchSize == 0 && i > 0) {
                session.flush();
                session.clear();
            }
        }
        session.flush();
        session.clear();
    }
}
