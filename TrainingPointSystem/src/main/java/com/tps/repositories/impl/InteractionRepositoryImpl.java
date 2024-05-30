package com.tps.repositories.impl;

import com.tps.pojo.Comment;
import com.tps.pojo.Post;
import com.tps.pojo.Reaction;
import com.tps.repositories.InteractionRepository;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@Transactional
public class InteractionRepositoryImpl implements InteractionRepository {

    @Autowired
    LocalSessionFactoryBean factoryBean;

    @Override
    public Reaction getReactionByUserPost(int userId, int postId) {
        Session session = factoryBean.getObject().getCurrentSession();
        Query query = session.createQuery("FROM Reaction WHERE user.id=:userId AND post.id=:postId");
        query.setParameter("userId", userId);
        query.setParameter("postId", postId);

        return (Reaction) query.uniqueResult();
    }

    @Override
    public List<Comment> getPostComments(int postId) {
        Session session = factoryBean.getObject().getCurrentSession();
        Post post = session.get(Post.class, postId);
        return post.getComments().stream().sorted(Comparator.comparing(Comment::getCreatedDate).reversed()).collect(Collectors.toList());
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

    @Override
    public boolean getLiked(int postId, int userId) {
        Session session = factoryBean.getObject().getCurrentSession();
        Query query = session.createQuery("FROM Reaction WHERE user.id=:userId AND post.id=:postId");
        query.setParameter("userId", userId);
        query.setParameter("postId", postId);

        Reaction reaction = (Reaction) query.uniqueResult();
        if (reaction != null) {
            return reaction.getIsActive();
        }

        return false;
    }

    @Override
    public int getCommentCount(int postId) {
        Session session = factoryBean.getObject().getCurrentSession();
        String hql = "SELECT COUNT(c.id) FROM Comment c WHERE c.post.id = :postId";
        Query<Long> query = session.createQuery(hql, Long.class);
        query.setParameter("postId", postId);
        Long count = query.uniqueResult();
        return count != null ? count.intValue() : 0;
    }

    @Override
    public int getLikeCount(int postId) {
        Session session = factoryBean.getObject().getCurrentSession();
        String hql = "SELECT COUNT(c.id) FROM Reaction c WHERE c.post.id = :postId  AND isActive=true";
        Query<Long> query = session.createQuery(hql, Long.class);
        query.setParameter("postId", postId);
        Long count = query.uniqueResult();
        return count != null ? count.intValue() : 0;
    }
}
