package com.tps.repositories.impl;


import com.tps.pojo.*;
import com.tps.pojo.Class;
import com.tps.repositories.StatsRepository;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import javax.persistence.ParameterMode;
import javax.persistence.Tuple;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
@Transactional
public class StatsRepositoryImpl implements StatsRepository {

    @Autowired
    private LocalSessionFactoryBean sessionFactory;

    @Override
    public List<Object[]> statsTrainingPoint(Map<String, String> params) {
        Session session = this.sessionFactory.getObject().getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Tuple> cq = cb.createTupleQuery();


        Root<Student> root = cq.from(Student.class);
        Join<Student, RegisterMission> registerMissionJoin = root.join("registerMission");
        Join<RegisterMission, Mission> missionJoin = registerMissionJoin.join("mission");
        Join<Mission, Activity> activityJoin = missionJoin.join("activity");
        Join<Activity, PointGroup> pointGroupJoin = activityJoin.join("pointGroup");
        Join<Student, Class> classJoin = root.join("class");
        Join<Student, Faculty> facultyJoin = classJoin.join("faculty");

        Expression<Integer> missionPoints = cb.sum(
                cb.<Integer>selectCase().when(cb.isTrue(registerMissionJoin.get("isCompleted")), missionJoin.get("point"))
                        .otherwise(0)
        );

        Expression<Integer> activityPoints = cb.<Integer>selectCase()
                .when(cb.greaterThan(missionPoints,activityJoin.get("maxPoint")),activityJoin.get("maxPoint"))
                .otherwise(missionPoints);


        Subquery<Integer> subquery = cq.subquery(Integer.class);
        Root<Student> studentSub = subquery.from(Student.class);
        Join<Student, RegisterMission> registerMissionSub = studentSub.join("registerMission");
        Join<RegisterMission, Mission> missionSub = registerMissionSub.join("mission");
        Join<Mission, Activity> activitySub = missionSub.join("activity");
        Join<Activity, PointGroup> pointGroupSub = activitySub.join("pointGroup");
        Join<Activity, Class> classSub = pointGroupSub.join("class");

        Expression<Integer> missionPointsSub = cb.sum(
                cb.<Integer>selectCase().when(cb.isTrue(registerMissionSub.get("isCompleted")), missionSub.get("point"))
                        .otherwise(0)
        );


        return session.createStoredProcedureQuery("CALL CalculateStudentPoints()").getResultList();
    }
}