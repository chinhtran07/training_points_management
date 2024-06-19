package com.tps.repositories.impl;


import com.tps.pojo.*;
import com.tps.pojo.Class;
import com.tps.repositories.StatsRepository;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import javax.persistence.Query;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@Transactional
public class StatsRepositoryImpl implements StatsRepository {

    private enum TypeStats {
        FACULTY, RANK, CLASS
    }

    @Autowired
    private LocalSessionFactoryBean sessionFactory;

    @Override
    public List<Object[]> statsTrainingPointByFaculty(Map<String, String> params) {
        return stats(TypeStats.CLASS, params);
    }

    @Override
    public List<Object[]> statsTrainingPointByRank(Map<String, String> params) {
        return stats(TypeStats.RANK, params);
    }

    @Override
    public List<Object[]> statsTrainingPoint(Map<String, String> params) {
        return stats(TypeStats.FACULTY, params);
    }

    @Override
    public List<Object[]> statsCountActivitiesByPointGroup(Map<String, String> params) {

        return List.of();
    }

    private List<Object[]> stats(TypeStats type, Map<String, String> params) {
        Session session = this.sessionFactory.getObject().getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Object[]> mainQuery = builder.createQuery(Object[].class);

        // Main query roots and joins
        Root<Student> studentRoot = mainQuery.from(Student.class);

        // Subquery for calculating mission points per student
        Subquery<Integer> missionPointsSubquery = mainQuery.subquery(Integer.class);
        {
            Root<Student> missionPointsStudentRoot = missionPointsSubquery.from(Student.class);
            Join<Student, RegisterMission> missionPointsRegisterJoin = missionPointsStudentRoot.join("registerMissions");
            Join<RegisterMission, Mission> missionPointsMissionJoin = missionPointsRegisterJoin.join("mission");
            Join<Mission, Activity> missionActivityJoin = missionPointsMissionJoin.join("activity");

            Expression<Integer> missionPoints = builder.sum(builder.<Integer>selectCase()
                    .when(builder.isTrue(missionPointsRegisterJoin.get("isCompleted")), missionPointsMissionJoin.get("point"))
                    .otherwise(0));

            List<Predicate> predicates = new ArrayList<>();
            String periodId = params.get("periodId");
            if (periodId != null && !periodId.isEmpty()) {
                predicates.add(builder.equal(missionActivityJoin.get("period").get("id"), periodId));
            }

            predicates.add(builder.equal(missionPointsStudentRoot.get("id"), studentRoot.get("id")));

            missionPointsSubquery.select(missionPoints);
            missionPointsSubquery.where(predicates.toArray(Predicate[]::new));
            missionPointsSubquery.groupBy(missionPointsStudentRoot.get("id"));
        }

        // Subquery for calculating activity points per student
        Subquery<Integer> activityPointsSubquery = mainQuery.subquery(Integer.class);
        {
            Root<Student> activityPointsStudentRoot = activityPointsSubquery.from(Student.class);
            Join<Student, RegisterMission> activityPointsRegisterJoin = activityPointsStudentRoot.join("registerMissions");
            Join<RegisterMission, Mission> activityPointsMissionJoin = activityPointsRegisterJoin.join("mission");
            Join<Mission, Activity> activityJoin = activityPointsMissionJoin.join("activity");

            Expression<Integer> activityPoints = builder.sum(builder.<Integer>selectCase()
                    .when(builder.greaterThan(missionPointsSubquery, activityJoin.get("maxPoint")), activityJoin.get("maxPoint"))
                    .otherwise(missionPointsSubquery));

            activityPointsSubquery.select(activityPoints);
            activityPointsSubquery.where(builder.equal(activityPointsStudentRoot.get("id"), studentRoot.get("id")));
            activityPointsSubquery.groupBy(activityPointsStudentRoot.get("id"), activityJoin.get("maxPoint"));
        }

        // Subquery for calculating total points per student
        Subquery<Integer> totalPointsSubquery = mainQuery.subquery(Integer.class);
        {
            Root<Student> totalPointsStudentRoot = totalPointsSubquery.from(Student.class);
            Join<Student, RegisterMission> totalPointsRegisterJoin = totalPointsStudentRoot.join("registerMissions");
            Join<RegisterMission, Mission> totalPointsMissionJoin = totalPointsRegisterJoin.join("mission");
            Join<Mission, Activity> totalPointsActivityJoin = totalPointsMissionJoin.join("activity");
            Join<Activity, PointGroup> pointGroupJoin = totalPointsActivityJoin.join("pointGroup");

            Expression<Integer> totalPoints = builder.sum(builder.<Integer>selectCase()
                    .when(builder.greaterThan(activityPointsSubquery, pointGroupJoin.get("maxPoint")), pointGroupJoin.get("maxPoint"))
                    .otherwise(activityPointsSubquery));

            totalPointsSubquery.select(totalPoints);
            totalPointsSubquery.where(builder.equal(totalPointsStudentRoot.get("id"), studentRoot.get("id")));
            totalPointsSubquery.groupBy(totalPointsStudentRoot.get("id"), pointGroupJoin.get("maxPoint"));
        }

        // Main query selection and aggregation

        if (type == TypeStats.CLASS) {
            Join<Student, Class> classJoin = studentRoot.join("classField");
            mainQuery.multiselect(
                    classJoin.get("name").alias("className"),
                    builder.countDistinct(studentRoot.get("id")).alias("numberOfStudent"),
                    builder.avg(totalPointsSubquery).alias("avgTotalPointPerClass")
            );
            String facultyId = params.get("facultyId");
            if (facultyId != null && !facultyId.isEmpty()) {
                mainQuery.where(builder.equal(studentRoot.get("faculty").get("id"), facultyId));
            }
            mainQuery.groupBy(classJoin.get("name"));
        }
        if (type == TypeStats.RANK) {
            mainQuery.multiselect(
                    builder.count(builder.selectCase().when(builder.between(totalPointsSubquery, 90, 100), 1)),
                    builder.count(builder.selectCase().when(builder.between(totalPointsSubquery, 80, 89), 1)),
                    builder.count(builder.selectCase().when(builder.between(totalPointsSubquery, 65, 79), 1)),
                    builder.count(builder.selectCase().when(builder.between(totalPointsSubquery, 50, 64), 1)),
                    builder.count(builder.selectCase().when(builder.between(totalPointsSubquery, 35, 49), 1)),
                    builder.count(builder.selectCase().when(builder.lessThan(totalPointsSubquery, 35), 1))
            );
            String facultyId = params.get("facultyId");
            if (facultyId != null && !facultyId.isEmpty()) {
                mainQuery.where(builder.equal(studentRoot.get("faculty").get("id"), facultyId));
            }
        }
        if (type == TypeStats.FACULTY) {
            Join<Student, Faculty> facultyJoin = studentRoot.join("faculty");
            mainQuery.multiselect(
                    facultyJoin.get("name").alias("facultyName"),
                    builder.countDistinct(studentRoot.get("id")).alias("numberOfStudents"),
                    builder.avg(totalPointsSubquery).alias("avgTotalPointPerFaculty")
            );
            mainQuery.groupBy(facultyJoin.get("name"));
        }

        Query query = session.createQuery(mainQuery);
        return query.getResultList();
    }

}