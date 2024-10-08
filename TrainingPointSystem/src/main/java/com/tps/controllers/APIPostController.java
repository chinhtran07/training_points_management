package com.tps.controllers;

import com.tps.components.CommentConverter;
import com.tps.components.PostConverter;
import com.tps.dto.CommentDTO;
import com.tps.dto.PostCreateDTO;
import com.tps.dto.PostDTO;
import com.tps.pojo.Comment;
import com.tps.pojo.Post;
import com.tps.pojo.Reaction;
import com.tps.pojo.User;
import com.tps.services.InteractionService;
import com.tps.services.PostService;
import com.tps.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/posts")
@CrossOrigin
public class APIPostController {
    @Autowired
    PostService postService;

    @Autowired
    PostConverter postConverter;

    @Autowired
    UserService userService;

    @Autowired
    InteractionService interactionService;

    @Autowired
    CommentConverter commentConverter;

    @GetMapping
    public ResponseEntity getPost(@RequestParam Map<String, String> params) {
        List<PostDTO> postDTOS = postService.getPost(params)
                .stream().map(p -> postConverter.convertToDTO(p))
                .collect(Collectors.toList());

        return new ResponseEntity(postDTOS, HttpStatus.OK);
    }

    @PostMapping(consumes = {
            MediaType.MULTIPART_FORM_DATA_VALUE
    })
    public ResponseEntity<PostDTO> addPost(@RequestParam HashMap<String, String> params,
                                           Principal principal,
                                           @RequestParam MultipartFile[] images) {
        PostCreateDTO postDTO = new PostCreateDTO();
        postDTO.setContent(params.get("content"));
        postDTO.setActivity(Integer.parseInt(params.get("activity")));
        postDTO.setAssistant(userService.getUserByUsername(principal.getName()).getId());

        return new ResponseEntity<>(postService.addPost(postDTO, images), HttpStatus.CREATED);
    }

    @GetMapping("/{postId}")
    public ResponseEntity detailPost(@PathVariable int postId) {
        Post post = postService.getPostById(postId);
        if (post == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(postConverter.convertToDTO(post), HttpStatus.OK);
    }

    @PutMapping("/{postId}")
    public ResponseEntity updatePost(@PathVariable int postId,
                                     Principal principal,
                                     @RequestBody PostCreateDTO postDTO) {
        if (postService.getPostById(postId) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        User u = userService.getUserByUsername(principal.getName());
        postDTO.setAssistant(u.getId());
        this.postService.updatePost(postId, postDTO);
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity deletePost(@PathVariable int postId) {
        if (postService.getPostById(postId) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        postService.deletePost(postId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{postId}/comments")
    public ResponseEntity getComments(@PathVariable int postId) {
        Post post = postService.getPostById(postId);
        if (post == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        List<CommentDTO> commentDTOS = this.interactionService.getPostComments(postId).stream().map(i -> commentConverter.toDTO(i)).collect(Collectors.toList());

        return new ResponseEntity(commentDTOS, HttpStatus.OK);
    }

    @PostMapping("/{postId}/comments")
    public ResponseEntity addComment(@PathVariable int postId,
                                     @RequestBody Map<String, String> params,
                                     Principal principal) {
        User u = userService.getUserByUsername(principal.getName());
        Post post = postService.getPostById(postId);
        if (post == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        Comment comment = new Comment();
        comment.setPost(post);
        comment.setUser(u);
        comment.setContent(params.get("content"));
        interactionService.addComment(comment);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PatchMapping("/comments/{commentId}")
    public ResponseEntity updateComment(@PathVariable int commentId,
                                        @RequestBody Map<String, String> params) {
        Comment comment = interactionService.getCommentById(commentId);
        if (comment == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        comment.setContent(params.get("content"));
        interactionService.updateComment(comment);
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity deleteComment(@PathVariable int commentId) {
        Comment comment = interactionService.getCommentById(commentId);
        if (comment == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        interactionService.deleteComment(comment);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/{postId}/like")
    public ResponseEntity likePost(@PathVariable int postId,
                                   Principal principal) {
        User user = userService.getUserByUsername(principal.getName());
        Reaction like = interactionService.getReactionByUserPost(user.getId(), postId);
        if (like == null) {
            like = new Reaction();
            like.setUser(user);
            like.setPost(postService.getPostById(postId));
            interactionService.addReaction(like);
            return new ResponseEntity(HttpStatus.CREATED);
        }

        like.setIsActive(!like.getIsActive());
        interactionService.updateReaction(like);
        return new ResponseEntity(HttpStatus.OK);
    }
}
