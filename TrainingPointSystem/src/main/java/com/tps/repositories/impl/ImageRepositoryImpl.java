package com.tps.repositories.impl;

import com.tps.pojo.Image;
import com.tps.repositories.ImageRepository;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class ImageRepositoryImpl implements ImageRepository {

    @Autowired
    LocalSessionFactoryBean factoryBean;

    @Override
    public void addImage(Image image) {
        Session session = factoryBean.getObject().getCurrentSession();
        session.save(image);
    }
}
