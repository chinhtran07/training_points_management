package com.tps.services.test;

import com.tps.pojo.Comment;
import com.tps.pojo.Reaction;
import com.tps.repositories.InteractionRepository;
import com.tps.services.impl.InteractionServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InteractionServiceImplTest {

    @Mock
    private InteractionRepository interactionRepository;

    @InjectMocks
    private InteractionServiceImpl interactionService;

    @Test
    void getReactionByUserPost() {
        int userId = 1;
        int postId = 1;
        Reaction expectedReaction = new Reaction();

        when(interactionRepository.getReactionByUserPost(userId, postId)).thenReturn(expectedReaction);

        Reaction actualReaction = interactionService.getReactionByUserPost(userId, postId);

        assertEquals(expectedReaction, actualReaction);
        verify(interactionRepository).getReactionByUserPost(userId, postId);
    }

    @Test
    void getPostComments() {
        int postId = 1;
        List<Comment> expectedComments = Collections.singletonList(new Comment());

        when(interactionRepository.getPostComments(postId)).thenReturn(expectedComments);

        List<Comment> actualComments = interactionService.getPostComments(postId);

        assertEquals(expectedComments, actualComments);
        verify(interactionRepository).getPostComments(postId);
    }

    @Test
    void getCommentById() {
        int commentId = 1;
        Comment expectedComment = new Comment();

        when(interactionRepository.getCommentById(commentId)).thenReturn(expectedComment);

        Comment actualComment = interactionService.getCommentById(commentId);

        assertEquals(expectedComment, actualComment);
        verify(interactionRepository).getCommentById(commentId);
    }

    @Test
    void updateComment() {
        Comment comment = new Comment();

        doNothing().when(interactionRepository).updateComment(comment);

        interactionService.updateComment(comment);

        verify(interactionRepository).updateComment(comment);
    }

    @Test
    void updateReaction() {
        Reaction reaction = new Reaction();
        doNothing().when(interactionRepository).updateReaction(reaction);
        interactionService.updateReaction(reaction);
        verify(interactionRepository).updateReaction(reaction);
    }

    @Test
    void deleteComment() {
        Comment comment = new Comment();
        doNothing().when(interactionRepository).deleteComment(comment);
        interactionService.deleteComment(comment);
        verify(interactionRepository).deleteComment(comment);
    }

    @Test
    void addComment() {
        Comment addedComment = new Comment();
        Comment expectedComment = new Comment();
        when(interactionRepository.addComment(addedComment)).thenReturn(expectedComment);
        Comment actualComment = interactionService.addComment(addedComment);
        assertEquals(expectedComment, actualComment);
        verify(interactionRepository).addComment(addedComment);
    }

    @Test
    void addReaction() {
        Reaction addedReaction = new Reaction();
        Reaction expectedReaction = new Reaction();
        when(interactionRepository.addReaction(addedReaction)).thenReturn(expectedReaction);
        Reaction actualReaction = interactionService.addReaction(addedReaction);
        assertEquals(expectedReaction, actualReaction);
        verify(interactionRepository).addReaction(addedReaction);
    }

    @Test
    void getLiked() {
        int userId = 1;
        int postId = 1;
        boolean expectedLiked = true;
        when(interactionRepository.getLiked(userId, postId)).thenReturn(expectedLiked);
        boolean actualLiked = interactionService.getLiked(userId, postId);
        assertEquals(expectedLiked, actualLiked);
    }

    @Test
    void getCommentCount() {
        int postId = 1;
        int expectedCount = 5;
        when(interactionRepository.getCommentCount(postId)).thenReturn(expectedCount);
        int actualCount = interactionService.getCommentCount(postId);
        assertEquals(expectedCount, actualCount);
        verify(interactionRepository).getCommentCount(postId);
    }

    @Test
    void getLikeCount() {
        int postId = 1;
        int expectedCount = 10;
        when(interactionRepository.getLikeCount(postId)).thenReturn(expectedCount);
        int actualCount = interactionService.getLikeCount(postId);
        assertEquals(expectedCount, actualCount);
        verify(interactionRepository).getLikeCount(postId);
    }
}