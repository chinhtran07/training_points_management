package com.tps.repositories.impl;


import com.tps.pojo.*;
import com.tps.repositories.StudentRepository;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import javax.persistence.criteria.*;
import java.util.List;

@Repository
@Transactional
public class StudentRepositoryImpl implements StudentRepository {

    @Autowired
    private LocalSessionFactoryBean sessionFactory;

    @Override
    public Student findStudentByStudentId(String studentId) {
        Session session = this.sessionFactory.getObject().getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Student> criteria = builder.createQuery(Student.class);
        Root<Student> root = criteria.from(Student.class);
        criteria.select(root);
        criteria.where(builder.equal(root.get("studentId"), studentId));
        Query query = session.createQuery(criteria);
        List<Student> results = query.getResultList();
        if (results.isEmpty()) {
            return null;
        } else {
            return results.get(0);
        }
    }

    @Override
    public List<Object> getResultOfTrainingPointById(int id) {
        Session session = this.sessionFactory.getObject().getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Object> cq = cb.createQuery(Object.class);

        Root<Student> root = cq.from(Student.class);
        Join<Student, RegisterMission> registerMissionJoin = root.join("registerMissions");
        Join<RegisterMission, Mission> missionJoin = registerMissionJoin.join("mission");
        Join<Mission, Activity> activityJoin = missionJoin.join("activity");
        Join<Activity, PointGroup> pointGroupJoin = activityJoin.join("pointGroup");

        cq.multiselect(
                pointGroupJoin.get("id"), pointGroupJoin.get("name"), pointGroupJoin.get("content")
                , pointGroupJoin.get("maxPoint"),
                activityJoin.get("id"), activityJoin.get("name"), activityJoin.get("maxPoint"),
                missionJoin.get("id"), missionJoin.get("name"), missionJoin.get("point")
        );

        cq.where(cb.isTrue(registerMissionJoin.get("isCompleted")), cb.equal(root.get("id"), id));

        cq.groupBy(pointGroupJoin.get("id"),
                activityJoin.get("id"),
                missionJoin.get("id"));
        cq.orderBy(cb.asc(pointGroupJoin.get("id")));


        Query query = session.createQuery(cq);
        return query.getResultList();
    }


    @Override
    public void addStudent(Student student) {
        Session session = this.sessionFactory.getObject().getCurrentSession();
    }
}
