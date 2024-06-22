package com.tps.services;

import com.tps.pojo.Comment;
import com.tps.pojo.Reaction;

import java.util.List;

public interface InteractionService {
    Reaction getReactionByUserPost(int userId, int postId);

    List<Comment> getPostComments(int postId);

    Comment getCommentById(int id);

    void updateComment(Comment comment);

    void updateReaction(Reaction like);

    void deleteComment(Comment comment);

    Comment addComment(Comment comment);

    Reaction addReaction(Reaction like);

    boolean getLiked(int postId, int ussrId);

    int getCommentCount(int postId);

    int getLikeCount(int postId);
}
