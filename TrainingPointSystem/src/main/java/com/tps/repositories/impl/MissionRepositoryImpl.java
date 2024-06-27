package com.tps.repositories.impl;

import com.tps.pojo.Mission;
import com.tps.pojo.RegisterMission;
import com.tps.repositories.MissionRepository;
import com.tps.services.ActivityService;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@Transactional
public class MissionRepositoryImpl implements MissionRepository {

    @Autowired
    LocalSessionFactoryBean factoryBean;

    @Autowired
    private ActivityService activityService;

    @Override
    public List<Mission> getMission(Map<String, String> params) {
        return null;
    }

    @Override
    public List<Object[]> getUserMission(int userId, Map<String, String> params) {
        Session session = factoryBean.getObject().getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();


        CriteriaQuery<RegisterMission> rmQuery = builder.createQuery(RegisterMission.class);
        Root<RegisterMission> rmRoot = rmQuery.from(RegisterMission.class);
        rmQuery.select(rmRoot);
        rmQuery.where(builder.equal(rmRoot.get("student"), userId));
        Query q = session.createQuery(rmQuery);

        List<RegisterMission> RegisterMissions = q.getResultList();

        CriteriaQuery<Mission> query = builder.createQuery(Mission.class);
        Root<Mission> missionRoot = query.from(Mission.class);
        query.select(missionRoot);
        List<Predicate> predicates = new ArrayList<>();

        String pointGroup = params.get("pointGroup");
        if (pointGroup != null && !pointGroup.isEmpty()) {
            predicates.add(builder.
                    equal(missionRoot.get("activity").get("pointGroup"), Integer.parseInt(pointGroup)));
        }

        String semester = params.get("semester");
        if (semester != null && !semester.isEmpty()) {
            predicates.add(builder.
                    equal(missionRoot.get("activity").get("semester"), Integer.parseInt(semester)));
        }

        query.where(predicates.toArray(Predicate[]::new));
        query.orderBy(builder.asc(missionRoot.get("activity").get("pointGroup")));
        List<Mission> allMission = session.createQuery(query).getResultList();

        Map<Integer, RegisterMission> registerMap = new HashMap<>();
        for (RegisterMission RegisterMission : RegisterMissions) {
            Integer missionId = RegisterMission.getMission().getId();
            registerMap.put(missionId, RegisterMission);
        }

        List<Object[]> result = new ArrayList<>();
        for (Mission mission : allMission) {
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
    public Mission addMission(Mission mission, int activityId) {
        Session session = factoryBean.getObject().getCurrentSession();
        mission.setActivity(this.activityService.getActivityById(activityId));
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
        mission.setIsActive(false);
        session.update(mission);
    }

    @Override
    public boolean checkMissionBelongToActivity(String activityId, String missionId) {
        Session session = this.factoryBean.getObject().getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Long> criteria = builder.createQuery(Long.class);

        Root<Mission> missionRoot = criteria.from(Mission.class);
        criteria.select(builder.count(missionRoot));
        List<Predicate> predicates = new ArrayList<>();

        predicates.add(builder.equal(missionRoot.get("activity").get("id"), Integer.parseInt(activityId)));
        predicates.add(builder.equal(missionRoot.get("id"), Integer.parseInt(missionId)));

        criteria.where(predicates.toArray(Predicate[]::new));

        Query q = session.createQuery(criteria);
        long count = (Long) q.getSingleResult();
        return count == 1;
    }

    @Override
    public List<Mission> getExpiredMissions(LocalDate currentTime) {
        Session session = this.factoryBean.getObject().getCurrentSession();

        if (currentTime == null) {
            return null;
        }

        Query query = session.createQuery("from Mission m where m.endDate >=: currentTime and m.isActive");
        query.setParameter("currentTime", currentTime);

        return query.getResultList();
    }
}
