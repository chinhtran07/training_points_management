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
import javax.persistence.Query;
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

    private String baseSQL() {
        StringBuilder sql = new StringBuilder("WITH activity_points AS (SELECT s.id AS student_id, " +
                "                                     p.id AS pointgroup_id, " +
                "                                     LEAST(SUM(CASE WHEN rm.is_completed = 1 THEN m.point ELSE 0 END), a.max_point) AS point " +
                "                               FROM student s " +
                "                               JOIN training_point.register_mission rm ON s.id = rm.student_id " +
                "                               JOIN training_point.mission m ON m.id = rm.mission_id " +
                "                               JOIN training_point.activity a ON m.activity_id = a.id " +
                "                               JOIN training_point.point_group p ON a.point_group_id = p.id " +
                "                               GROUP BY student_id, pointgroup_id, a.max_point), " +
                "point_group_points AS (SELECT s.id AS student_id, LEAST(SUM(ap.point), pg.max_point) AS point " +
                "                       FROM student s " +
                "                       JOIN activity_points ap ON s.id = ap.student_id " +
                "                       JOIN training_point.point_group pg ON ap.pointgroup_id = pg.id " +
                "                       GROUP BY s.id, pg.max_point)," +
                "student_total_points AS (SELECT s.id AS student_id, SUM(pgp.point) AS total_points" +
                "                         FROM student s" +
                "                         JOIN point_group_points pgp ON s.id = pgp.student_id" +
                "                         GROUP BY s.id)");

        return sql.toString();
    }

    @Override
    public List<Object[]> statsTrainingPointByFaculty(String facultyId) {
        Session session = this.sessionFactory.getObject().getCurrentSession();
        String sql = baseSQL() +
                "SELECT c.name, " +
                "       count(distinct stp.student_id) as classStudents," +
                "       avg(stp.total_points) as avgTotalPointClass " +
                "FROM student_total_points stp " +
                "join student s on stp.student_id = s.id " +
                "join class c on s.class_id = c.id " +
                "where s.faculty_id = :param " +
                "GROUP BY c.name";

        Query query = session.createNativeQuery(sql);
        query.setParameter("param", facultyId);
        return query.getResultList();
    }

    @Override
    public List<Object[]> statsTrainingPointByRank() {
        Session session = this.sessionFactory.getObject().getCurrentSession();
        String sql = baseSQL() +
                "SELECT " +
                "    COUNT(CASE WHEN stp.total_points BETWEEN 90 AND 100 THEN 1 END) AS Excellent," +
                "    COUNT(CASE WHEN stp.total_points BETWEEN 80 AND 89 THEN 1 END) AS Good," +
                "    COUNT(CASE WHEN stp.total_points BETWEEN 65 AND 79 THEN 1 END) AS Fair," +
                "    COUNT(CASE WHEN stp.total_points BETWEEN 50 AND 64 THEN 1 END) AS Average," +
                "    COUNT(CASE WHEN stp.total_points BETWEEN 35 AND 49 THEN 1 END) AS Weak," +
                "    COUNT(CASE WHEN stp.total_points < 35 THEN 1 END) AS Poor " +
                "FROM student_total_points stp;";

        Query query = session.createNativeQuery(sql);
        return query.getResultList();
    }

    @Override
    public List<Object[]> statsTrainingPoint() {
        Session session = this.sessionFactory.getObject().getCurrentSession();
        String sql = baseSQL() +
                "SELECT" +
                "    f.name AS faculty_name," +
                "    COUNT(CASE WHEN stp.total_points BETWEEN 90 AND 100 THEN 1 END) AS Excellent," +
                "    COUNT(CASE WHEN stp.total_points BETWEEN 80 AND 89 THEN 1 END) AS Good," +
                "    COUNT(CASE WHEN stp.total_points BETWEEN 65 AND 79 THEN 1 END) AS Fair," +
                "    COUNT(CASE WHEN stp.total_points BETWEEN 50 AND 64 THEN 1 END) AS Average," +
                "    COUNT(CASE WHEN stp.total_points BETWEEN 35 AND 49 THEN 1 END) AS Weak," +
                "    COUNT(CASE WHEN stp.total_points < 35 THEN 1 END) AS Poor," +
                "    COUNT(DISTINCT stp.student_id) AS TotalStudents," +
                "    AVG(stp.total_points) AS AvgTotalPoints " +
                "FROM student_total_points stp" +
                "         JOIN student s ON stp.student_id = s.id" +
                "         JOIN faculty f ON s.faculty_id = f.id " +
                "GROUP BY f.name";

        Query query = session.createNativeQuery(sql);
        return query.getResultList();
    }
}