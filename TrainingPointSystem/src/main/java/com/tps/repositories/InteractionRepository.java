package com.tps.repositories;

import com.tps.pojo.Comment;
import com.tps.pojo.Reaction;

public interface InteractionRepository {
    Reaction getReactionByUserPost(int userId, int postId);

    Comment getCommentById(int id);

    Comment addComment(Comment comment);

    Reaction addReaction(Reaction like);

    void updateComment(Comment comment);

    void updateReaction(Reaction like);

    void deleteComment(Comment comment);
}
