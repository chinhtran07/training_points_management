package com.tps.repositories.impl;

import com.tps.pojo.PointGroup;
import com.tps.repositories.PointGroupRepository;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
@Transactional
public class PointGroupRepositoryImpl implements PointGroupRepository {

    @Autowired
    private LocalSessionFactoryBean sessionFactory;

    @Override
    public void addOrUpdate(PointGroup pointGroup) {
        Session session = this.sessionFactory.getObject().getCurrentSession();
        if (pointGroup.getId() == null) {
            session.save(pointGroup);
        }
        session.update(pointGroup);
    }

    @Override
    public void delete(PointGroup pointGroup) {
        Session session = this.sessionFactory.getObject().getCurrentSession();
        session.delete(pointGroup);
    }

    @Override
    public PointGroup getPointGroup(int id) {
        Session session = this.sessionFactory.getObject().getCurrentSession();
        PointGroup pointGroup = session.get(PointGroup.class, id);
        return pointGroup;
    }

    @Override
    public List<PointGroup> getAllPointGroups() {
        Session session = this.sessionFactory.getObject().getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<PointGroup> cq = cb.createQuery(PointGroup.class);
        Root<PointGroup> root = cq.from(PointGroup.class);

        cq.select(root);

        Query q = session.createQuery(cq);
        List<PointGroup> pointGroups = q.getResultList();
        return pointGroups;
    }
}
