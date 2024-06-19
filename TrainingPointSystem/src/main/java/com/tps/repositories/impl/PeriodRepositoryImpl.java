package com.tps.repositories.impl;

import com.tps.pojo.Period;
import com.tps.repositories.PeriodRepository;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class PeriodRepositoryImpl implements PeriodRepository {

    @Autowired
    private LocalSessionFactoryBean sessionFactory;

    @Override
    public List<String> getYears() {
        Session session = this.sessionFactory.getObject().getCurrentSession();
        String hql = "select p.year from Period p group by p.year";

        Query query = session.createQuery(hql);

        return query.getResultList();
    }

    @Override
    public Period getPeriod(String year, int semesterId) {
        Session session = this.sessionFactory.getObject().getCurrentSession();
        String hql = "from Period p where p.year = :year and p.semester = :semester";

        Query query = session.createQuery(hql);
        query.setParameter("year", year);
        query.setParameter("semester", semesterId);

        return (Period) query.getSingleResult();
    }

    @Override
    public List<Period> getAllPeriods(String year) {
        Session session = this.sessionFactory.getObject().getCurrentSession();
        Query query = session.createQuery("from Period p where p.year = :year and p.isActive is true");
        query.setParameter("year", year);
        return query.getResultList();
    }
}
