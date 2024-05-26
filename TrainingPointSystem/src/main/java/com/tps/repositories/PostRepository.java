package com.tps.repositories;

import com.tps.pojo.Post;

import java.util.List;
import java.util.Map;

public interface PostRepository {
    List<Post> getPost(Map<String, String> params);

    Post getPostById(int postId);

    Post addPost(Post post);

    void updatePost(Post post);

    void deletePost(int postId);
}
