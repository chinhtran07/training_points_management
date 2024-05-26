package com.tps.services;

import com.tps.dto.PostCreateDTO;
import com.tps.dto.PostDTO;
import com.tps.pojo.Post;

import java.util.List;
import java.util.Map;

public interface PostService {
    List<Post> getPost(Map<String, String> params);

    PostDTO addPost(PostCreateDTO postDTO);

    void updatePost(int postId, PostCreateDTO postDTO);

    void deletePost(int postId);

    Post getPostById(int postId);
}