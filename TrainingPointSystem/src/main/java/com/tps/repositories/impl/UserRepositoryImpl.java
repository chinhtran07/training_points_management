package com.tps.repositories.impl;

import com.tps.pojo.User;
import com.tps.repositories.UserRepository;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class UserRepositoryImpl implements UserRepository {
    @Autowired
    private LocalSessionFactoryBean factoryBean;

    @Override
    public User getUserByUsername(String username) {
        Session s = factoryBean.getObject().getCurrentSession();
        Query q = s.createQuery("from User where username=:username");
        q.setParameter("username", username);

        return (User) q.uniqueResult();
    }
}
