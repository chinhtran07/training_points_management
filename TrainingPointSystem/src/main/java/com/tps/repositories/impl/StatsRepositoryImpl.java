package com.tps.repositories.impl;


import com.tps.dto.StudentTotalPointsDTO;
import com.tps.dto.TotalPointPerPg;
import com.tps.pojo.*;
import com.tps.pojo.Class;
import com.tps.repositories.StatsRepository;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.criteria.*;
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
        String sql = "with missions_point_per_activity as (" +
                "   select s.id as student_id, m.activity_id, m.point as missions_point " +
                "   from registermission r " +
                "       join training_point.mission m on m.id = r.mission_id " +
                "       join training_point.student s on s.id = r.student_id " +
                "   where r.is_completed = true), " +
                "total_point_per_activity as (" +
                "   select mppa.student_id, a.pointgroup_id as pointgroup_id, " +
                "       sum(least(mppa.missions_point, a.max_point)) as activity_point " +
                "   from missions_point_per_activity mppa " +
                "       join activity a on mppa.activity_id = a.id " +
                "   group by mppa.student_id, pointgroup_id), " +
                "total_point_per_pg as (" +
                "   select tppa.student_id, sum(least(tppa.activity_point, p.max_point)) as total_point" +
                "   from total_point_per_activity tppa" +
                "       join pointgroup p on tppa.pointgroup_id = p.id" +
                "   group by tppa.student_id)" +
                "select s.id as id, s.student_id as studentId, u.first_name as firstName, u.last_name as lastName, c.name as className, f.name as facultynName," +
                "       least(sum(tppp.total_point), 100) as totalPoint " +
                "from student s" +
                "   left join total_point_per_pg tppp on s.id = tppp.student_id " +
                "   join user u on s.id = u.id " +
                "   join class c on s.faculty_id = c.id " +
                "   join faculty f on s.faculty_id = f.id " +
                "where c.id = :classId and f.id = :facultyId " +
                "group by s.id " +
                "having totalPoint > :target";

        Query query = session.createNativeQuery(sql);
        String classId = params.get("classId");
        if (classId != null && !classId.isEmpty()) {
            query.setParameter("classId", Integer.parseInt(classId));
        } else {
            query.setParameter("classId", 1);
        }

        String facultyId = params.get("facultyId");
        if (facultyId != null && !facultyId.isEmpty()) {
            query.setParameter("facultyId", Integer.parseInt(facultyId));
        } else {
            query.setParameter("facultyId", 1);
        }

        String target = params.get("target");
        if (target != null && !target.isEmpty()) {
            query.setParameter("target", Integer.parseInt(target));
        } else {
            query.setParameter("target", 0);
        }

        return query.getResultList();
    }
}