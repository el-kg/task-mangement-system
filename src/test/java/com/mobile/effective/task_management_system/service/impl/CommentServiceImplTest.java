package com.mobile.effective.task_management_system.service.impl;


import com.mobile.effective.task_management_system.model.Comment;
import com.mobile.effective.task_management_system.repository.CommentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CommentServiceImplTest {

    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private CommentServiceImpl commentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addComment_ShouldReturnAddedComment() {

        Comment comment = new Comment();
        comment.setContent("Test comment");
        when(commentRepository.save(comment)).thenReturn(comment);


        Comment addedComment = commentService.addComment(comment);


        assertEquals("Test comment", addedComment.getContent());
        verify(commentRepository).save(comment);
    }

    @Test
    void getCommentByTaskId_ShouldReturnListOfComments() {

        Long taskId = 1L;
        Comment comment = new Comment();
        comment.setContent("Test comment");
        List<Comment> comments = Collections.singletonList(comment);
        when(commentRepository.findByTaskId(taskId)).thenReturn(comments);


        List<Comment> result = commentService.getCommentByTaskId(taskId);


        assertEquals(1, result.size());
        assertEquals("Test comment", result.get(0).getContent());
        verify(commentRepository).findByTaskId(taskId);
    }
}