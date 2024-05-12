package com.tps.repositories.impl;


import com.tps.pojo.Faculty;
import com.tps.repositories.FacultyRepository;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Repository
@Transactional
public class FacultyRepositoryImpl implements FacultyRepository {

    @Autowired
    private LocalSessionFactoryBean sessionFactory;


    @Override
    public void addFaculty(Faculty faculty) {
        Session session = this.sessionFactory.getObject().getCurrentSession();
        session.save(faculty);
    }

    @Override
    public void updateFaculty(Faculty faculty) {
        Session session = this.sessionFactory.getObject().getCurrentSession();
        session.update(faculty);
    }

    @Override
    public void deleteFaculty(Faculty faculty) {
        Session session = this.sessionFactory.getObject().getCurrentSession();
        session.delete(faculty);
    }

    @Override
    public Faculty getFacultyById(int id) {
        Session session = this.sessionFactory.getObject().getCurrentSession();
        return (Faculty) session.get(Faculty.class, id);
    }

    @Override
    public List<Faculty> getAllFaculty(Map<String, String> params) {
        Session session = this.sessionFactory.getObject().getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Faculty> cq = cb.createQuery(Faculty.class);
        Root<Faculty> root = cq.from(Faculty.class);

        List<Predicate> predicates = new ArrayList<Predicate>();

        String kw = params.get("kw");
        if (kw != null && !kw.isEmpty()) {
            predicates.add(cb.like(root.get("name"), "%" + kw + "%"));
        }

        cq.where(predicates.toArray(Predicate[]::new));

        Query<Faculty> query = session.createQuery(cq);

        return query.getResultList();
    }
}
