package com.tps.services.test;

import com.cloudinary.Cloudinary;
import com.cloudinary.Uploader;
import com.cloudinary.utils.ObjectUtils;
import com.tps.components.PostConverter;
import com.tps.dto.PostCreateDTO;
import com.tps.dto.PostDTO;
import com.tps.pojo.Post;
import com.tps.repositories.PostRepository;
import com.tps.services.ImageService;
import com.tps.services.PostService;
import com.tps.services.impl.PostServiceImpl;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostServiceImplTest {
    @Mock
    private PostRepository postRepository;

    @Mock
    private PostConverter postConverter;

    @Mock
    private Cloudinary cloudinary;

    @Mock
    private Uploader uploader;

    @Mock
    private ImageService imageService;

    @Mock
    private PostService postService;

    @InjectMocks
    private PostServiceImpl postServiceImpl;

    @Test
    void getPost() {
        Map<String, String> params = new HashMap<>();
        List<Post> expectedPosts = Arrays.asList(new Post(), new Post());
        when(postRepository.getPost(params)).thenReturn(expectedPosts);

        // When
        List<Post> actualPosts = postServiceImpl.getPost(params);

        // Then
        assertEquals(expectedPosts, actualPosts);
        verify(postRepository, times(1)).getPost(params);
    }

    @SneakyThrows
    @Test
    void addPost() {
        PostCreateDTO postDTO = new PostCreateDTO();
        MultipartFile[] images = new MultipartFile[]{mock(MultipartFile.class)};
        Post post = new Post();
        post.setId(1);
        when(postConverter.toEntity(postDTO)).thenReturn(post);
        when(postService.getPostById(post.getId())).thenReturn(post);
        when(postConverter.convertToDTO(post)).thenReturn(new PostDTO());
        when(cloudinary.uploader()).thenReturn(uploader);

        Map<String, String> uploadResult = new HashMap<>();
        uploadResult.put("secure_url", "http://image.url");
        when(uploader.upload(any(byte[].class), ObjectUtils.asMap("resource_type", "auto"))).thenReturn(uploadResult);

        // When
        PostDTO actualPostDTO = postServiceImpl.addPost(postDTO, images);

        // Then
        verify(postRepository, times(1)).addPost(post);
        verify(imageService, times(1)).addImage(post.getId(), "http://image.url");
        assertNotNull(actualPostDTO);
    }

    @Test
    void updatePost() {
        int postId = 1;
        PostCreateDTO postDTO = new PostCreateDTO();
        Post post = new Post();
        when(postConverter.toEntity(postDTO)).thenReturn(post);

        // When
        postServiceImpl.updatePost(postId, postDTO);

        // Then
        verify(postConverter, times(1)).toEntity(postDTO);
        verify(postRepository, times(1)).updatePost(post);
        assertEquals(postId, post.getId());
    }

    @Test
    void deletePost() {
        int postId = 1;

        // When
        postServiceImpl.deletePost(postId);

        // Then
        verify(postRepository, times(1)).deletePost(postId);
    }

    @Test
    void getPostById() {
        int postId = 1;
        Post expectedPost = new Post();
        when(postRepository.getPostById(postId)).thenReturn(expectedPost);

        // When
        Post actualPost = postServiceImpl.getPostById(postId);

        // Then
        assertEquals(expectedPost, actualPost);
        verify(postRepository, times(1)).getPostById(postId);
    }
}