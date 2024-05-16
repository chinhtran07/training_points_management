package com.tps.repositories.impl;


import com.tps.repositories.StatsRepository;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;



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
        String hql = "select s.id, s.user.firstName, s.user.lastName, s.classField.name, s.faculty.name, " +
                "sum(case when tppa.totalPointsPerPg > pg.maxPoint then pg.maxPoint else tppa.totalPointsPerPg end) " +
                "from Student s " +
                "join (" +
                "   select s.id as studentId, m.activity.id as activityId, " +
                "   sum(m.point) as missionsPoint " +
                "   from Registermission r " +
                "   join r.student s " +
                "   join r.mission m " +
                "   group by s.id, m.activity.id" +
                ") as mppa on mppa.studentId = s.id " +
                "join (" +
                "   select mppa.studentId as studentId, a.pointgroup.id as pointgroupId, " +
                "   sum(case when mppa.missionsPoint > a.maxPoint then a.maxPoint else mppa.missionsPoint end) as totalPointsPerPg " +
                "   from (" +
                "       select s.id as studentId, m.activity.id as activityId, " +
                "       sum(m.point) as missionsPoint " +
                "       from Registermission r " +
                "       join r.student s " +
                "       join r.mission m " +
                "       group by s.id, m.activity.id" +
                "   ) as mppa " +
                "   join Activity a on mppa.activityId = a.id " +
                "   group by mppa.studentId, a.pointgroup.id" +
                ") as tppa on tppa.studentId = s.id " +
                "join Pointgroup pg on tppa.pointgroupId = pg.id " +
                "where s.faculty.id = :facultyId and s.classField.id = :classId " +
                "group by s.id, s.user.firstName, s.user.lastName, s.classField.name, s.faculty.name";

        Query query = session.createQuery(hql);
        String facultyId = params.get("facultyId");
        if (facultyId != null && !facultyId.isEmpty()) {
            query.setParameter("facultyId", facultyId);
        }
        String classId = params.get("classId");
        if (classId != null && !classId.isEmpty()) {
            query.setParameter("classId", classId);
        }

        return query.getResultList();
    }
}