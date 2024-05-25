package com.tps.components;

import com.tps.dto.PostCreateDTO;
import com.tps.dto.PostDTO;
import com.tps.pojo.Post;
import com.tps.services.ActivityService;
import com.tps.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class PostConverter {
    @Autowired
    ActivityConverter activityConverter;

    @Autowired
    ImageConverter imageConverter;

    @Autowired
    UserConverter userConverter;

    @Autowired
    UserService userService;

    @Autowired
    ActivityService activityService;

    public Post toEntity(PostDTO postDTO) {
        Post post = new Post();
        post.setId(postDTO.getId());
        post.setContent(postDTO.getContent());
        post.setActivity(activityConverter.toEntity(postDTO.getActivity()));
        post.setImages(postDTO.getImages()
                .stream().map(i->imageConverter.toEntity(i))
                .collect(Collectors.toList()));

        return post;
    }

    public Post toEntity(PostCreateDTO postCreateDTO) {
        Post post = new Post();
        post.setContent(postCreateDTO.getContent());
        post.setUser(userService.getUserById(postCreateDTO.getAssistant()));
        post.setActivity(activityService.getActivityById(postCreateDTO.getActivity()));

        return post;
    }

    public PostDTO convertToDTO(Post post) {
        PostDTO postDTO = new PostDTO();
        postDTO.setId(post.getId());
        postDTO.setContent(post.getContent());
//        postDTO.setAssistant(post.getAssistant().convertToDTO());
        postDTO.setActivity(activityConverter.toDTO(post.getActivity()));
        postDTO.setCreatedDate(post.getCreatedDate().toString());
        if(postDTO.getImages() != null) {
            postDTO.setImages(post.getImages()
                    .stream().map(i -> imageConverter.toDTO(i))
                    .collect(Collectors.toList()));
        }

        return postDTO;
    }
}
