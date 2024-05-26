package com.tps.repositories.impl;


import com.tps.pojo.RegisterMission;
import com.tps.repositories.RegisterMissionRepository;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Optional;

@Repository
@Transactional
public class RegisterMissionRepositoryImpl implements RegisterMissionRepository {

    @Autowired
    private LocalSessionFactoryBean sessionFactory;

    @Override
    public void addOrUpdateStatus(RegisterMission mission) {
        Session session = this.sessionFactory.getObject().getCurrentSession();
        session.saveOrUpdate(mission);
    }

    @Override
    public RegisterMission findById(int studentId, int missionId) {
        Session session = this.sessionFactory.getObject().getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<RegisterMission> cq = cb.createQuery(RegisterMission.class);
        Root<RegisterMission> root = cq.from(RegisterMission.class);
        cq.where(cb.equal(root.get("studentId"), studentId));
        cq.where(cb.equal(root.get("missionId"), missionId));

        return session.createQuery(cq).getSingleResult();
    }
}
