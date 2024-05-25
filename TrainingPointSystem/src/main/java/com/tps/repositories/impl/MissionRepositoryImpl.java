package com.tps.repositories.impl;

import com.tps.pojo.Mission;
import com.tps.pojo.Registermission;
import com.tps.repositories.MissionRepository;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@Transactional
public class MissionRepositoryImpl implements MissionRepository {

    @Autowired
    LocalSessionFactoryBean factoryBean;

    @Override
    public List<Mission> getMission(Map<String, String> params) {
        return null;
    }

    @Override
    public List<Object[]> getUserMission(int userId, Map<String, String> params) {
        Session session = factoryBean.getObject().getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();


        CriteriaQuery<Registermission> rmQuery = builder.createQuery(Registermission.class);
        Root<Registermission> rmRoot = rmQuery.from(Registermission.class);
        rmQuery.select(rmRoot);
        rmQuery.where(builder.equal(rmRoot.get("student"), userId));
        Query q = session.createQuery(rmQuery);

        List<Registermission> registermissions = q.getResultList();

        CriteriaQuery<Mission> query = builder.createQuery(Mission.class);
        Root<Mission> missionRoot = query.from(Mission.class);
        query.select(missionRoot);
        List<Predicate> predicates = new ArrayList<>();

        String pointGroup = params.get("pointGroup");
        if (pointGroup != null && !pointGroup.isEmpty()) {
            predicates.add(builder.
                    equal(missionRoot.get("activity").get("pointgroup"), Integer.parseInt(pointGroup)));
        }

        String semester = params.get("semester");
        if (semester != null && !semester.isEmpty()) {
            predicates.add(builder.
                    equal(missionRoot.get("activity").get("semester"), Integer.parseInt(semester)));
        }

        query.where(predicates.toArray(Predicate[]::new));
        query.orderBy(builder.asc(missionRoot.get("activity").get("pointgroup")));
        List<Mission> allMission = session.createQuery(query).getResultList();

        Map<Integer, Registermission> registerMap = new HashMap<>();
        for (Registermission registermission : registermissions) {
            Integer missionId = registermission.getMission().getId();
            registerMap.put(missionId, registermission);
        }

        List<Object[]> result = new ArrayList<>();
        for (Mission mission: allMission) {
            Object[] obj = new Object[2];
            obj[0] = mission;
            obj[1] = registerMap.get(mission.getId());
            result.add(obj);
        }

        return result;
    }

    @Override
    public Mission getMissionById(int id) {
        Session session = factoryBean.getObject().getCurrentSession();
        return session.get(Mission.class, id);
    }

    @Override
    public Mission addMission(Mission mission) {
        Session session = factoryBean.getObject().getCurrentSession();
        session.save(mission);
        return mission;
    }

    @Override
    public Mission updateMission(Mission mission) {
        Session session = factoryBean.getObject().getCurrentSession();
        session.update(mission);
        return mission;
    }

    @Override
    public void deleteMission(int id) {
        Session session = factoryBean.getObject().getCurrentSession();
        Mission mission = session.get(Mission.class, id);
        session.delete(mission);
    }
}
