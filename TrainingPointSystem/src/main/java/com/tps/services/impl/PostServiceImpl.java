package com.tps.services.impl;

import com.tps.components.PostConverter;
import com.tps.dto.PostCreateDTO;
import com.tps.dto.PostDTO;
import com.tps.pojo.Post;
import com.tps.repositories.PostRepository;
import com.tps.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    PostRepository postRepository;

    @Autowired
    PostConverter postConverter;

    @Override
    public List<Post> getPost(Map<String, String> params) {
        return this.postRepository.getPost(params);
    }


    @Override
    public PostDTO addPost(PostCreateDTO postDTO) {
        Post post = this.postRepository.addPost(postConverter.toEntity(postDTO));

        return postConverter.convertToDTO(post);
    }

    @Override
    public void updatePost(int postId, PostCreateDTO postDTO) {

        Post post = postConverter.toEntity(postDTO);
        post.setId(postId);
        this.postRepository.updatePost(post);
    }

    @Override
    public void deletePost(int postId) {
        this.postRepository.deletePost(postId);
    }

    @Override
    public Post getPostById(int postId) {
        return this.postRepository.getPostById(postId);
    }
}
