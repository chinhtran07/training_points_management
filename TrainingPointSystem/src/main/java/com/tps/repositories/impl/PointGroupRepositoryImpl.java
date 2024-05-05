package com.tps.repositories.impl;

import com.tps.pojo.Pointgroup;
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
    public void addOrUpdate(Pointgroup pointgroup) {
        Session session = this.sessionFactory.getObject().getCurrentSession();
        if (pointgroup.getId() == null) {
            session.save(pointgroup);
        }
        session.update(pointgroup);
    }

    @Override
    public void delete(Pointgroup pointgroup) {
        Session session = this.sessionFactory.getObject().getCurrentSession();
        session.delete(pointgroup);
    }

    @Override
    public Pointgroup getPointgroup(int id) {
        Session session = this.sessionFactory.getObject().getCurrentSession();
        Pointgroup pointgroup = (Pointgroup) session.get(Pointgroup.class, id);
        return pointgroup;
    }

    @Override
    public List<Pointgroup> getAllPointGroups() {
        Session session = this.sessionFactory.getObject().getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Pointgroup> cq = cb.createQuery(Pointgroup.class);
        Root<Pointgroup> root = cq.from(Pointgroup.class);

        cq.select(root);

        Query q = session.createQuery(cq);
        List<Pointgroup> pointgroups = q.getResultList();
        return pointgroups;
    }
}
