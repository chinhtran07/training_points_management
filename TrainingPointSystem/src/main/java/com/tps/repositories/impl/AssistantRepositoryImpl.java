package com.tps.repositories.impl;

import com.tps.pojo.Assistant;
import com.tps.pojo.Faculty;
import com.tps.repositories.AssistantRepository;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
@Transactional
public class AssistantRepositoryImpl implements AssistantRepository {

    @Autowired
    private LocalSessionFactoryBean factoryBean;

    @Override
    public void addAssistant(Assistant assistant) {
        Session session = this.factoryBean.getObject().getCurrentSession();
        session.save(assistant);
    }

    @Override
    public void updateAssistant(Assistant assistant) {
        Session session = this.factoryBean.getObject().getCurrentSession();
        session.update(assistant);
    }

    @Override
    public void deleteAssistant(Assistant assistant) {
        Session session = this.factoryBean.getObject().getCurrentSession();
        session.delete(assistant);
    }

    @Override
    public List<Assistant> getAllAssistants(Map<String, String> params) {
        Session session = this.factoryBean.getObject().getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Assistant> cq = cb.createQuery(Assistant.class);
        Root<Assistant> root = cq.from(Assistant.class);

        Query<Assistant> query = session.createQuery(cq);

        return query.getResultList();
    }

    @Override
    public Assistant getAssistantById(int id) {
        Session session = this.factoryBean.getObject().getCurrentSession();

        return session.get(Assistant.class, id);
    }

    @Override
    public List<Object[]> getUserAssistants(Map<String, String> params) {
        Session session = this.factoryBean.getObject().getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteria = builder.createQuery(Object[].class);

        Root<Assistant> userRoot = criteria.from(Assistant.class);
        Join<Assistant, Faculty> facultyJoin = userRoot.join("faculty");

        criteria.multiselect(
                userRoot.get("id"),
                userRoot.get("user").get("username"),
                userRoot.get("user").get("firstName"),
                userRoot.get("user").get("lastName"),
                facultyJoin.get("name"),
                userRoot.get("user").get("isActive")
        );

        Query<Object[]> query = session.createQuery(criteria);
        return query.getResultList();
    }
}
