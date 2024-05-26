package com.tps.repositories.impl;

import com.tps.pojo.Post;
import com.tps.repositories.PostRepository;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Map;

@Repository
@Transactional
@PropertySource("classpath:configs.properties")
public class PostRepositoryImpl implements PostRepository {
    @Autowired
    private Environment env;

    @Autowired
    private LocalSessionFactoryBean sessionFactory;

    @Override
    public List<Post> getPost(Map<String, String> params) {
        Session session = sessionFactory.getObject().getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Post> criteriaQuery = builder.createQuery(Post.class);
        Root<Post> root = criteriaQuery.from(Post.class);

        criteriaQuery.select(root);

        criteriaQuery.orderBy(builder.asc(root.get("createdDate")));
        Query query = session.createQuery(criteriaQuery);

        String page = params.get("page");
        if(page != null && !page.isEmpty()) {
            int pageSize = Integer.parseInt(env.getProperty("post.pageSize").toString());
            int start = (Integer.parseInt(page) - 1) * pageSize;
            query.setFirstResult(start);
            query.setMaxResults(pageSize);
        }

        return query.getResultList();
    }

    @Override
    public Post getPostById(int postId) {
        Session session = sessionFactory.getObject().getCurrentSession();
        return session.get(Post.class, postId);
    }

    @Override
    public Post addPost(Post post) {
        Session session = sessionFactory.getObject().getCurrentSession();
        session.save(post);
        return (post) ;
    }

    @Override
    public void updatePost(Post post) {
        Session session = sessionFactory.getObject().getCurrentSession();
        session.update(post);
    }


    @Override
    public void deletePost(int postId) {
        Session session = sessionFactory.getObject().getCurrentSession();
        Post post = session.get(Post.class, postId);
        session.delete(post);
    }
}
