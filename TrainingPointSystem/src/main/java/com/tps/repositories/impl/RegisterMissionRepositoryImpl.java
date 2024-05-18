package com.tps.repositories.impl;


import com.tps.pojo.Registermission;
import com.tps.pojo.RegistermissionId;
import com.tps.repositories.RegisterMissionRepository;
import org.hibernate.Session;
import org.hibernate.query.Query;
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
    public void addOrUpdateStatus(Registermission mission) {
        Session session = this.sessionFactory.getObject().getCurrentSession();
        session.saveOrUpdate(mission);
    }

    @Override
    public Registermission findById(int studentId, int missionId) {
        Session session = this.sessionFactory.getObject().getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Registermission> cq = cb.createQuery(Registermission.class);
        Root<Registermission> root = cq.from(Registermission.class);
        cq.where(cb.equal(root.get("studentId"), studentId));
        cq.where(cb.equal(root.get("missionId"), missionId));

        return session.createQuery(cq).getSingleResult();
    }
}
