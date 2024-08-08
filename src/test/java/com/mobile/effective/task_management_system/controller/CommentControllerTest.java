package com.mobile.effective.task_management_system.controller;

import com.mobile.effective.task_management_system.model.Comment;
import com.mobile.effective.task_management_system.service.CommentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CommentController.class)
public class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CommentService commentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @WithMockUser
    void addComment_ShouldReturnCreatedComment() throws Exception {

        Comment comment = new Comment();
        comment.setContent("Test comment");
        when(commentService.addComment(any(Comment.class))).thenReturn(comment);


        mockMvc.perform(post("/api/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"content\":\"Test comment\"}")
                        .with(csrf()))  // добавление CSRF токена
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value("Test comment"));

        verify(commentService).addComment(any(Comment.class));
    }

    @Test
    @WithMockUser
    void getCommentsByTaskId_ShouldReturnListOfComments() throws Exception {

        Long taskId = 1L;
        Comment comment = new Comment();
        comment.setContent("Test comment");
        List<Comment> comments = Collections.singletonList(comment);
        when(commentService.getCommentByTaskId(taskId)).thenReturn(comments);


        mockMvc.perform(get("/api/comments/task/{taskId}", taskId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].content").value("Test comment"));

        verify(commentService).getCommentByTaskId(taskId);
    }
}