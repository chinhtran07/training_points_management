package com.tps.services.impl;

import com.tps.pojo.Comment;
import com.tps.pojo.Reaction;
import com.tps.repositories.InteractionRepository;
import com.tps.services.InteractionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InteractionServiceImpl implements InteractionService {
    @Autowired
    InteractionRepository interactionRepository;

    @Override
    public Reaction getReactionByUserPost(int userId, int postId) {
        return this.interactionRepository.getReactionByUserPost(userId, postId);
    }

    @Override
    public List<Comment> getPostComments(int postId) {
        return this.interactionRepository.getPostComments(postId);
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
    public void updateReaction(Reaction like) {
        this.interactionRepository.updateReaction(like);
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
    public Reaction addReaction(Reaction like) {
        return this.interactionRepository.addReaction(like);
    }

    @Override
    public boolean getLiked(int postId, int ussrId) {
        return this.interactionRepository.getLiked(postId, ussrId);
    }

    @Override
    public int getCommentCount(int postId) {
        return this.interactionRepository.getCommentCount(postId);
    }

    @Override
    public int getLikeCount(int postId) {
        return this.interactionRepository.getLikeCount(postId);
    }
}
