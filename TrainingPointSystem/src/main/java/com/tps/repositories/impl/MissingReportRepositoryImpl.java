package com.tps.repositories.impl;

import com.tps.pojo.*;
import com.tps.repositories.MissingReportRepository;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
@Transactional
public class MissingReportRepositoryImpl implements MissingReportRepository {

    @Autowired
    private LocalSessionFactoryBean sessionFactory;

    @Override
    public List<Object[]> getMissionReportByFaculty(int facultyId) {
        Session session = this.sessionFactory.getObject().getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteria = builder.createQuery(Object[].class);

        Root<Student> root = criteria.from(Student.class);
        Join<Student, User> userJoin = root.join("user");
        Join<Student, Faculty> facultyJoin = root.join("faculty");
        Join<Student, MissingReport> missingReportJoin = root.join("missingReports");
        Join<MissingReport, Mission> missionJoin = missingReportJoin.join("mission");

        criteria.multiselect(
                userJoin.get("lastName"),
                userJoin.get("lastName"),
                root.get("studentId"),
                missionJoin.get("id"),
                missingReportJoin.get("updatedDate"),
                missingReportJoin.get("isActive")
        );

        criteria.where(builder.equal(facultyJoin.get("id"), facultyId));

        criteria.orderBy(builder.asc(missionJoin.get("updatedDate")));

        criteria.groupBy(userJoin.get("lastName"),
                userJoin.get("lastName"),
                root.get("studentId"),
                missionJoin.get("id"),
                missingReportJoin.get("updatedDate"),
                missingReportJoin.get("isActive"));

        Query query = session.createQuery(criteria);

        return query.getResultList();
    }
}
