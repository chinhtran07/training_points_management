package com.tps.repositories.impl;


import com.tps.pojo.Activity;
import com.tps.repositories.ActivityRepository;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
@Transactional
@PropertySource("classpath:configs.properties")
public class ActivityRepositoryImpl implements ActivityRepository {

    @Autowired
    private LocalSessionFactoryBean sessionFactory;

    @Autowired
    private Environment env;

    @Override
    public int addActivity(Activity activity) {
        Session session = this.sessionFactory.getObject().getCurrentSession();
        session.save(activity);
        Activity a = (Activity) session.getIdentifier(activity);
        return a.getId();
    }

    @Override
    public List<Activity> getActivities(Map<String, String> params) {
        Session session = this.sessionFactory.getObject().getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Activity> criteria = builder.createQuery(Activity.class);
        Root<Activity> root = criteria.from(Activity.class);
        criteria.select(root);

        List<Predicate> predicates = new ArrayList<>();

        String kw = params.get("kw");
        if (kw != null && !kw.isEmpty()) {
            predicates.add(builder.like(root.get("name"), String.format("%%%s%%", kw)));
        }

        String pointGroupId = params.get("pointGroupId");
        if (pointGroupId != null && !pointGroupId.isEmpty()) {
            predicates.add(builder.equal(root.get("pointGroupId"), Integer.parseInt(pointGroupId)));
        }

        criteria.where(predicates.toArray(Predicate[]::new));
        criteria.orderBy(builder.asc(root.get("id")));

        Query query = session.createQuery(criteria);

        String page = params.get("page");
        if(page != null && !page.isEmpty()) {
            int pageSize = Integer.parseInt(env.getProperty("activities.pageSize"));
            int start = (Integer.parseInt(page) - 1) * pageSize;
            query.setFirstResult(start);
            query.setMaxResults(pageSize);
        }

        List<Activity> activities = query.getResultList();

        return activities;
    }

    @Override
    public Activity getActivityById(int id) {
        Session session = this.sessionFactory.getObject().getCurrentSession();
        return session.get(Activity.class, id);
    }

    @Override
    public Activity updateActivity(Activity activity) {
        Session session = this.sessionFactory.getObject().getCurrentSession();
        session.update(activity);
        return activity;
    }

    @Override
    public void deleteActivity(int id) {
        Session session = this.sessionFactory.getObject().getCurrentSession();
        Activity activity = this.getActivityById(id);
        session.delete(activity);
    }

    @Override
    public List<Activity> findByExpirationDateBeforeAndIsActive(Instant currentDate) {
        Session session = this.sessionFactory.getObject().getCurrentSession();
        List<Predicate> predicates = new ArrayList<>();

        if (currentDate == null) {
            return null;
        }

        Query query = session.createQuery("from Activity where updatedDate >= :currentDate or createdDate >= :currentDate and isActive");
        List<Activity> activities = query.getResultList();

        return activities;
    }
}
