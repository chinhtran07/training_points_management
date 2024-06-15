package com.tps.services.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.tps.components.PostConverter;
import com.tps.dto.PostCreateDTO;
import com.tps.dto.PostDTO;
import com.tps.pojo.Image;
import com.tps.pojo.Post;
import com.tps.repositories.PostRepository;
import com.tps.services.ImageService;
import com.tps.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    PostRepository postRepository;

    @Autowired
    PostConverter postConverter;

    @Autowired
    Cloudinary cloudinary;

    @Autowired
    ImageService imageService;

    @Autowired
    PostService postService;

    @Override
    public List<Post> getPost(Map<String, String> params) {
        return this.postRepository.getPost(params);
    }


    @Override
    public PostDTO addPost(PostCreateDTO postDTO, MultipartFile[] images) {
        Post post = postConverter.toEntity(postDTO);
        postRepository.addPost(post);
        if (images.length > 0) {
            List<Image> postImages = new ArrayList<>();
            Arrays.stream(images).forEach(image -> {
                Map res;
                try {
                    res = this.cloudinary.uploader().upload(image.getBytes(),
                            ObjectUtils.asMap("resource_type", "auto"));
                    imageService.addImage(post.getId(), (String) res.get("secure_url"));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            post.setImages(postImages);
        }

        return postConverter.convertToDTO(postService.getPostById(post.getId()));
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
