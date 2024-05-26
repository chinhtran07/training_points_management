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
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

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
    public void addOrUpdateStatus(RegisterMission mission) {
        Session session = this.factoryBean.getObject().getCurrentSession();
        session.saveOrUpdate(mission);
    }

    @Override
    public RegisterMission findById(int studentId, int missionId) {
        Session session = this.factoryBean.getObject().getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<RegisterMission> cq = cb.createQuery(RegisterMission.class);
        Root<RegisterMission> root = cq.from(RegisterMission.class);
        cq.where(cb.equal(root.get("studentId"), studentId));
        cq.where(cb.equal(root.get("missionId"), missionId));

        return session.createQuery(cq).getSingleResult();
    }
}
