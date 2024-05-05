package com.tps.repositories.impl;

import com.tps.pojo.User;
import com.tps.repositories.UserRepository;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository
@Transactional
public class UserRepositoryImpl implements UserRepository {
    @Autowired
    private LocalSessionFactoryBean factoryBean;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public User getUserByUsername(String username) {
        Session s = factoryBean.getObject().getCurrentSession();
        Query q = s.createQuery("from User where username=:username");
        q.setParameter("username", username);

        return (User) q.uniqueResult();
    }

    @Override
    public boolean authUser(String username, String password) {
        User u = this.getUserByUsername(username);
        if (u != null && u.getIsActive()) {
            return this.passwordEncoder.matches(password, u.getPassword());
        }
        return false;
    }

    @Override
    public User addUser(User user) {
        Session s = factoryBean.getObject().getCurrentSession();
        s.save(user);

        return user;
    }

    @Override
    public User findById(int id) {
        Session s = Objects.requireNonNull(factoryBean.getObject()).getCurrentSession();
        return s.get(User.class, id);
    }

    @Override
    public void updateUser(User user) {
        Session session = this.factoryBean.getObject().getCurrentSession();
        session.saveOrUpdate(user);
    }

    @Override
    public void deleteUser(User user) {
        Session session = this.factoryBean.getObject().getCurrentSession();
        session.delete(user);
    }

    @Override
    public List<User> getAllUsers(Map<String, String> params) {
        Session s = this.factoryBean.getObject().getCurrentSession();
        CriteriaBuilder builder = s.getCriteriaBuilder();
        CriteriaQuery<User> criteria = builder.createQuery(User.class);
        Root<User> root = criteria.from(User.class);
        criteria.select(root);

        List<Predicate> predicates = new ArrayList<>();

        String kw = params.get("kw");
        if (kw != null && !kw.isEmpty()) {
            Predicate containsName = builder.or(
                    builder.like(builder.concat(root.get("firstName"), " "), "%" + kw + "%"),
                    builder.like(builder.concat(root.get("lastName"), " "), "%" + kw + "%")
            );
            predicates.add(containsName);
        }

        String gender = params.get("gender");
        if (gender != null && !gender.isEmpty()) {
            predicates.add(builder.equal(root.get("gender"), gender));
        }

        if (params.containsKey("isStudent")) {
            Boolean isStudent = Boolean.parseBoolean(params.get("isStudent"));
            predicates.add(builder.equal(root.get("isStudent"), isStudent));
        }

        if (params.containsKey("isAssistant")) {
            Boolean isAssistant = Boolean.parseBoolean(params.get("isAssistant"));
            predicates.add(builder.equal(root.get("isAssistant"), isAssistant));
        }

        if (params.containsKey("isSuperuser")) {
            Boolean isSuperuser = Boolean.parseBoolean(params.get("isSuperuser"));
            predicates.add(builder.equal(root.get("isSuperuser"), isSuperuser));
        }

        predicates.add(builder.equal(root.get("isActive"), true));

        criteria.where(predicates.toArray(Predicate[]::new));
        criteria.orderBy(builder.asc(root.get("id")));


        Query<User> query = s.createQuery(criteria);
        return query.getResultList();
    }
}
