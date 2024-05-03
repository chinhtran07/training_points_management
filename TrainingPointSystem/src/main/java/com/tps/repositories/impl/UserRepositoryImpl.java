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
}
