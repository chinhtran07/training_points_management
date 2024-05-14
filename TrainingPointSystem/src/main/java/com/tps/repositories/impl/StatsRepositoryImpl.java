package com.tps.repositories.impl;

import com.tps.pojo.*;
import com.tps.pojo.Class;
import com.tps.repositories.StatsRepository;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


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
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Object[]> cq = builder.createQuery(Object[].class);
        Root<Student> studentRoot = cq.from(Student.class);
        Join<Student, Class> classJoin = studentRoot.join("class");
        Join<Student, Faculty> facultyJoin = studentRoot.join("faculty");


        //Subquery for missions_points_per_activity
        Subquery<Integer> missionPointsSubquery = cq.subquery(Integer.class);
        Root<Registermission> registermissionRoot = missionPointsSubquery.from(Registermission.class);
        Join<Registermission, Mission> missionJoin = registermissionRoot.join("mission");

        missionPointsSubquery.select(
                builder.sum(missionJoin.get("point"))
        ).where(
                builder.equal(studentRoot.get("id"), registermissionRoot.get("student").get("id"))
        ).groupBy(
                registermissionRoot.get("student").get("id"),
                missionJoin.get("activity").get("id")
        );

        //Subquery for total_points_per_activity
        Subquery<Integer> totalPointsSubquery = cq.subquery(Integer.class);
        Root<Activity> activityRoot = totalPointsSubquery.from(Activity.class);
        Join<Activity, Pointgroup> pointgroupJoin = activityRoot.join("pointgroup");

        Expression<Integer> totalPoints = builder.sum(
                builder.<Integer>selectCase()
                        .when(builder.greaterThan(missionPointsSubquery.getSelection(), activityRoot.get("maxPoint")), activityRoot.get("maxPoint"))
                        .otherwise(missionPointsSubquery.getSelection())
        );

        totalPointsSubquery.select(totalPoints).where(
                builder.equal(activityRoot.get("id"), missionJoin.get("activity").get("id"))
        ).groupBy(
                activityRoot.get("id"),
                pointgroupJoin.get("id")
        );

        //Main query
        Expression<Integer> finalPoints = builder.sum(
                builder.<Integer>selectCase()
                        .when(builder.greaterThan(totalPointsSubquery.getSelection(), pointgroupJoin.get("maxPoint")), pointgroupJoin.get("maxPoint"))
                        .otherwise(totalPointsSubquery.getSelection())
        );

        cq.multiselect(
                studentRoot.get("id"),
                studentRoot.get("user").get("firstName"),
                studentRoot.get("user").get("lastName"),
                classJoin.get("name"),
                facultyJoin.get("name"),
                finalPoints
        ).groupBy(
                studentRoot.get("id"),
                classJoin.get("name"),
                facultyJoin.get("name")
        );

        List<Predicate> predicates = new ArrayList<>();

        String faculty = params.get("faculty");
        if (faculty != null && !faculty.isEmpty()) {
            predicates.add(builder.equal(facultyJoin.get("faculty"), faculty));
        }

        String className = params.get("class");
        if (className != null && !className.isEmpty()) {
            predicates.add(builder.equal(classJoin.get("class"), className));
        }

        String from = params.get("from");
        if (from != null && !from.isEmpty()) {
            predicates.add(builder.greaterThan(finalPoints, Integer.parseInt(from)));
        }

        String to = params.get("to");
        if (to != null && !to.isEmpty()) {
            predicates.add(builder.lessThan(finalPoints, Integer.parseInt(to)));
        }

        Query<Object[]> query = session.createQuery(cq);

        return query.getResultList();
    }
}