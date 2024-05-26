package com.tps.services.impl;

import com.tps.pojo.Comment;
import com.tps.pojo.Like;
import com.tps.repositories.InteractionRepository;
import com.tps.services.InteractionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InteractionServiceImpl implements InteractionService {
    @Autowired
    InteractionRepository interactionRepository;

    @Override
    public Like getLikeByUserPost(int userId, int postId) {
        return this.interactionRepository.getLikeByUserPost(userId, postId);
    }

    @Override
    public Comment getCommentById(int id) {
        return this.interactionRepository.getCommentById(id);
    }

    @Override
    public void updateComment(Comment comment) {
        this.interactionRepository.updateComment(comment);
    }

    @Override
    public void updateLike(Like like) {
        this.interactionRepository.updateLike(like);
    }

    @Override
    public void deleteComment(Comment comment) {
        this.interactionRepository.deleteComment(comment);
    }

    @Override
    public Comment addComment(Comment comment) {
        return this.interactionRepository.addComment(comment);
    }

    @Override
    public Like addLike(Like like) {
        return this.interactionRepository.addLike(like);
    }
}
