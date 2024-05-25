package com.tps.repositories;

import com.tps.pojo.Comment;
import com.tps.pojo.Like;

public interface InteractionRepository {
    Like getLikeByUserPost(int userId, int postId);

    Comment getCommentById(int id);


    Comment addComment(Comment comment);

    Like addLike(Like like);

    void updateComment(Comment comment);

    void updateLike(Like like);

    void deleteComment(Comment comment);
}
