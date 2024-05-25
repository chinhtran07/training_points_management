package com.tps.services;

import com.tps.pojo.Comment;
import com.tps.pojo.Like;

public interface InteractionService {
    Like getLikeByUserPost(int userId, int postId);

    Comment getCommentById(int id);

    void updateComment(Comment comment);

    void updateLike(Like like);

    void deleteComment(Comment comment);

    Comment addComment(Comment comment);

    Like addLike(Like like);
}
