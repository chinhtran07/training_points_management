package com.tps.repositories.impl;

import com.tps.pojo.Class;
import com.tps.pojo.Faculty;
import com.tps.pojo.Student;
import com.tps.repositories.ClassRepository;
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
import java.util.Map;

@Repository
@Transactional
public class ClassRepositoryImpl implements ClassRepository {

    @Autowired
    private LocalSessionFactoryBean sessionFactory;

    @Override
    public void addClass(Class c) {
        Session session = this.sessionFactory.getObject().getCurrentSession();
        session.save(c);
    }

    @Override
    public void updateClass(Class c) {
        Session session = this.sessionFactory.getObject().getCurrentSession();
        session.update(c);
    }

    @Override
    public void deleteClass(Class c) {
        Session session = this.sessionFactory.getObject().getCurrentSession();
        session.delete(c);
    }

    @Override
    public Class getClassById(int id) {
        Session session = this.sessionFactory.getObject().getCurrentSession();
        return session.get(Class.class, id);
    }

    @Override
    public List<Class> getAllClass(Map<String, String> params) {
        Session session = this.sessionFactory.getObject().getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Class> criteria = builder.createQuery(Class.class);
        Root<Class> root = criteria.from(Class.class);

        criteria.select(root);

        Query<Class> query = session.createQuery(criteria);

        return query.getResultList();
    }

    @Override
    public List<Class> getClassesByFaculty(int facultyId) {
        Session session = this.sessionFactory.getObject().getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Class> criteria = builder.createQuery(Class.class);
        Root<Class> root = criteria.from(Class.class);

        Join<Class, Student> classStudentJoin = root.join("students");
        Join<Student, Faculty> studentFacultyJoin = classStudentJoin.join("faculty");

        criteria.multiselect(
                root.get("id"),
                root.get("name")
        );

        criteria.where(builder.equal(studentFacultyJoin.get("id"), facultyId));

        Query query = session.createQuery(criteria);

        return query.getResultList();
    }
}
