package com.mobile.effective.task_management_system.controller;

import com.mobile.effective.task_management_system.model.Task;
import com.mobile.effective.task_management_system.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TaskController.class)
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @WithMockUser
    void createTask_ShouldReturnCreatedTask() throws Exception {

        Task task = new Task();
        task.setTitle("Test Task");
        when(taskService.createTask(any(Task.class))).thenReturn(task);


        mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"Test Task\"}")
                        .with(csrf()))  // добавление CSRF токена
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test Task"));

        verify(taskService).createTask(any(Task.class));
    }

    @Test
    @WithMockUser
    void getTasksByAuthorId_ShouldReturnPageOfTasks() throws Exception {

        Long authorId = 1L;
        Task task = new Task();
        task.setTitle("Test Task");
        Page<Task> tasks = new PageImpl<>(Collections.singletonList(task), PageRequest.of(0, 10), 1);
        when(taskService.getTasksByAuthorId(authorId, PageRequest.of(0, 10))).thenReturn(tasks);


        mockMvc.perform(get("/api/tasks/author/{authorId}", authorId)
                        .param("page", "0")
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].title").value("Test Task"));

        verify(taskService).getTasksByAuthorId(authorId, PageRequest.of(0, 10));
    }

    @Test
    @WithMockUser
    void getTasksByAssigneeId_ShouldReturnPageOfTasks() throws Exception {

        Long assigneeId = 1L;
        Task task = new Task();
        task.setTitle("Test Task");
        Page<Task> tasks = new PageImpl<>(Collections.singletonList(task), PageRequest.of(0, 10), 1);
        when(taskService.getTasksByAssigneeId(assigneeId, PageRequest.of(0, 10))).thenReturn(tasks);


        mockMvc.perform(get("/api/tasks/assignee/{assigneeId}", assigneeId)
                        .param("page", "0")
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].title").value("Test Task"));

        verify(taskService).getTasksByAssigneeId(assigneeId, PageRequest.of(0, 10));
    }

    @Test
    @WithMockUser
    void getTaskById_ShouldReturnTask() throws Exception {

        Long taskId = 1L;
        Task task = new Task();
        task.setTitle("Test Task");
        when(taskService.getTaskById(taskId)).thenReturn(Optional.of(task));

        mockMvc.perform(get("/api/tasks/{taskId}", taskId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test Task"));

        verify(taskService).getTaskById(taskId);
    }

    @Test
    @WithMockUser
    void updateTask_ShouldReturnUpdatedTask() throws Exception {

        Long taskId = 1L;
        Task task = new Task();
        task.setTitle("Updated Task");
        when(taskService.updateTask(anyLong(), any(Task.class))).thenReturn(task);


        mockMvc.perform(put("/api/tasks/{taskId}", taskId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"Updated Task\"}")
                        .with(csrf()))  // добавление CSRF токена
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Task"));

        verify(taskService).updateTask(anyLong(), any(Task.class));
    }

    @Test
    @WithMockUser
    void deleteTask_ShouldReturnNoContent() throws Exception {

        Long taskId = 1L;


        mockMvc.perform(delete("/api/tasks/{taskId}", taskId)
                        .with(csrf()))  // добавление CSRF токена
                .andExpect(status().isNoContent());

        verify(taskService).deleteTask(taskId);
    }
}