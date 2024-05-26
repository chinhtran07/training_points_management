package com.tps.services;

import com.tps.pojo.Comment;
import com.tps.pojo.Reaction;

public interface InteractionService {
    Reaction getReactionByUserPost(int userId, int postId);

    Comment getCommentById(int id);

    void updateComment(Comment comment);

    void updateReaction(Reaction like);

    void deleteComment(Comment comment);

    Comment addComment(Comment comment);

    Reaction addReaction(Reaction like);
}
