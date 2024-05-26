package com.tps.controllers;

import com.tps.components.CommentConverter;
import com.tps.components.PostConverter;
import com.tps.dto.CommentDTO;
import com.tps.dto.PostCreateDTO;
import com.tps.dto.PostDTO;
import com.tps.pojo.Comment;
import com.tps.pojo.Like;
import com.tps.pojo.Post;
import com.tps.pojo.User;
import com.tps.services.InteractionService;
import com.tps.services.PostService;
import com.tps.services.UserService;
import jdk.javadoc.doclet.Reporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/posts")
public class APIPostController {
    @Autowired
    PostService postService;

    @Autowired
    PostConverter postConverter;

    @Autowired
    UserService userService;

    @Autowired
    InteractionService interactionService;

    @GetMapping
    public ResponseEntity getPost(@RequestParam Map<String, String> params) {
        List<PostDTO> postDTOS = postService.getPost(params)
                .stream().map(p -> postConverter.convertToDTO(p))
                .collect(Collectors.toList());

        return new ResponseEntity(postDTOS, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<PostDTO> addPost(@RequestBody PostCreateDTO postDTO, Principal principal) {
        postDTO.setAssistant(userService.getUserByUsername(principal.getName()).getId());
        return new ResponseEntity<>(postService.addPost(postDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{postId}")
    public ResponseEntity updatePost(@PathVariable int postId,
                                     @RequestBody PostCreateDTO postDTO) {
        if (postService.getPostById(postId) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
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
        comment.setStudent(u.getStudent());
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
        Like like = interactionService.getLikeByUserPost(user.getId(), postId);
        if (like == null) {
            like = new Like();
            like.setStudent(user.getStudent());
            like.setPost(postService.getPostById(postId));
            interactionService.addLike(like);
            return new ResponseEntity(HttpStatus.CREATED);
        }

        like.setIsActive(!like.getIsActive());
        interactionService.updateLike(like);
        return new ResponseEntity(HttpStatus.OK);
    }
}
