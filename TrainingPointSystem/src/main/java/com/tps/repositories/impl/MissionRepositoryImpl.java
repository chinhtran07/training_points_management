package com.tps.repositories.impl;

import com.tps.pojo.Mission;
import com.tps.repositories.MissionRepository;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class MissionRepositoryImpl implements MissionRepository {

    @Autowired
    private LocalSessionFactoryBean sessionFactory;

    @Override
    public void addOrUpdateMission(Mission mission) {
        Session session = this.sessionFactory.getObject().getCurrentSession();
        session.saveOrUpdate(mission);
    }

    @Override
    public boolean checkMissionBelongToActivity(int activityId, int missionId) {
        Session session = this.sessionFactory.getObject().getCurrentSession();
        String hql = "select count(m) from Mission m where m.id =: missionId and m.activity.id =: activityId";
        Query<Integer> query = session.createQuery(hql, Integer.class);
        query.setParameter("missionId", missionId);
        query.setParameter("activityId", activityId);
        int count = query.uniqueResult();
        return count > 0;
    }
}
