package com.tps.repositories;

import com.tps.pojo.Comment;
import com.tps.pojo.Reaction;

import java.util.List;

public interface InteractionRepository {
    Reaction getReactionByUserPost(int userId, int postId);

    List<Comment> getPostComments(int postId);
    Comment getCommentById(int id);

    Comment addComment(Comment comment);

    Reaction addReaction(Reaction like);

    void updateComment(Comment comment);

    void updateReaction(Reaction like);

    void deleteComment(Comment comment);

    boolean getLiked(int postId, int ussrId);
    int getCommentCount(int postId);
    int getLikeCount(int postId);
}
