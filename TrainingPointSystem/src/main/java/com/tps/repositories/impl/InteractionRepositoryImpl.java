package com.tps.repositories.impl;

import com.tps.pojo.Comment;
import com.tps.pojo.Reaction;
import com.tps.pojo.Reaction;
import com.tps.repositories.InteractionRepository;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class InteractionRepositoryImpl implements InteractionRepository {

    @Autowired
    LocalSessionFactoryBean factoryBean;

    @Override
    public Reaction getReactionByUserPost(int userId, int postId) {
        Session session = factoryBean.getObject().getCurrentSession();
        Query query = session.createQuery("FROM Reaction WHERE student.id=:studentId AND post.id=:postId");
        query.setParameter("studentId", userId);
        query.setParameter("postId", postId);

        return (Reaction) query.uniqueResult();
    }

    @Override
    public Comment getCommentById(int id) {
        Session session = factoryBean.getObject().getCurrentSession();
        return session.get(Comment.class, id);
    }


    @Override
    public Comment addComment(Comment comment) {
        Session session = factoryBean.getObject().getCurrentSession();
        session.save(comment);
        return comment;
    }

    @Override
    public Reaction addReaction(Reaction like) {
        Session session = factoryBean.getObject().getCurrentSession();
        session.save(like);
        return like;
    }

    @Override
    public void updateComment(Comment comment) {
        Session session = factoryBean.getObject().getCurrentSession();
        session.update(comment);
    }

    @Override
    public void updateReaction(Reaction like) {
        Session session = factoryBean.getObject().getCurrentSession();
        session.update(like);
    }

    @Override
    public void deleteComment(Comment comment) {
        Session session = factoryBean.getObject().getCurrentSession();
        session.delete(comment);
    }
}
