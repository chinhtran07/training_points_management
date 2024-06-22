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
        assistant.getUser().setIsActive(false);
        session.update(assistant);
    }

    @Override
    public List<Assistant> getAllAssistants(Map<String, String> params) {
        Session session = this.factoryBean.getObject().getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Assistant> cq = cb.createQuery(Assistant.class);
        Root<Assistant> root = cq.from(Assistant.class);

        cq.where(cb.isTrue(root.get("user").get("isActive")));
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

        List<Predicate> predicates = new ArrayList<>();

        String facultyId = params.get("facultyId");
        if (facultyId != null && !facultyId.isEmpty()) {
            predicates.add(builder.equal(facultyJoin.get("id"), Integer.parseInt(facultyId)));
        }

        String kw = params.get("kw");
        if (kw != null && !kw.isEmpty()) {
            predicates.add(builder.like(userRoot.get("user").get("username"), String.format("%%%s%%", kw)));
        }

        String activate = params.get("isActive");
        if (activate != null && !activate.isEmpty()) {
            if (activate.equals("true"))
                predicates.add(builder.isTrue(userRoot.get("user").get("isActive")));
            else
                predicates.add(builder.isFalse(userRoot.get("user").get("isActive")));
        }

        criteria.where(predicates.toArray(Predicate[]::new));

        Query<Object[]> query = session.createQuery(criteria);
        return query.getResultList();
    }
}
