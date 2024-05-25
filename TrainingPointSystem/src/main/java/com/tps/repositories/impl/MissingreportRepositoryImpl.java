package com.tps.repositories.impl;

import com.tps.pojo.Missingreport;
import com.tps.pojo.Registermission;
import com.tps.repositories.MissingReportRepository;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class MissingreportRepositoryImpl implements MissingReportRepository {
    @Autowired
    LocalSessionFactoryBean factoryBean;

    @Override
    public Missingreport getMissingByStudentMission(int studentId, int missionId) {
        Session session = factoryBean.getObject().getCurrentSession();
        Query query = session.createQuery("FROM Missingreport WHERE student.id=:studentId AND mission.id=:missionId");
        query.setParameter("studentId", studentId);
        query.setParameter("missionId", missionId);
        return (Missingreport) query.uniqueResult();
    }

    @Override
    public void updateMissingreport(Missingreport missingreport) {
        Session session = factoryBean.getObject().getCurrentSession();
        session.update(missingreport);
    }

    @Override
    public Missingreport addMissingreport(Missingreport missingreport) {
        Session session = factoryBean.getObject().getCurrentSession();
        session.save(missingreport);
        return missingreport;
    }
}
