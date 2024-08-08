package com.mobile.effective.task_management_system.service.impl;


import com.mobile.effective.task_management_system.model.Task;
import com.mobile.effective.task_management_system.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TaskServiceImplTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskServiceImpl taskService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createTask_ShouldReturnCreatedTask() {

        Task task = new Task();
        task.setTitle("Test Task");
        when(taskRepository.save(task)).thenReturn(task);


        Task createdTask = taskService.createTask(task);


        assertEquals(task.getTitle(), createdTask.getTitle());
        verify(taskRepository).save(task);
    }

    @Test
    void getTasksByAuthorId_ShouldReturnPageOfTasks() {

        Long authorId = 1L;
        Pageable pageable = PageRequest.of(0, 10);
        Task task = new Task();
        Page<Task> page = new PageImpl<>(Collections.singletonList(task));
        when(taskRepository.findByAuthorId(authorId, pageable)).thenReturn(page);


        Page<Task> result = taskService.getTasksByAuthorId(authorId, pageable);


        assertEquals(1, result.getTotalElements());
        verify(taskRepository).findByAuthorId(authorId, pageable);
    }

    @Test
    void getTasksByAssigneeId_ShouldReturnPageOfTasks() {

        Long assigneeId = 1L;
        Pageable pageable = PageRequest.of(0, 10);
        Task task = new Task();
        Page<Task> page = new PageImpl<>(Collections.singletonList(task));
        when(taskRepository.findByAssigneeId(assigneeId, pageable)).thenReturn(page);


        Page<Task> result = taskService.getTasksByAssigneeId(assigneeId, pageable);


        assertEquals(1, result.getTotalElements());
        verify(taskRepository).findByAssigneeId(assigneeId, pageable);
    }

    @Test
    void getTaskById_ShouldReturnTask_WhenTaskExists() {

        Long taskId = 1L;
        Task task = new Task();
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));


        Optional<Task> result = taskService.getTaskById(taskId);


        assertTrue(result.isPresent());
        assertEquals(task, result.get());
        verify(taskRepository).findById(taskId);
    }

    @Test
    void getTaskById_ShouldReturnEmpty_WhenTaskDoesNotExist() {

        Long taskId = 1L;
        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());


        Optional<Task> result = taskService.getTaskById(taskId);


        assertFalse(result.isPresent());
        verify(taskRepository).findById(taskId);
    }

    @Test
    void updateTask_ShouldReturnUpdatedTask_WhenTaskExists() {

        Long taskId = 1L;
        Task existingTask = new Task();
        existingTask.setId(taskId);
        Task updatedDetails = new Task();
        updatedDetails.setTitle("Updated Title");
        updatedDetails.setDescription("Updated Description");
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(existingTask));
        when(taskRepository.save(existingTask)).thenReturn(existingTask);


        Task updatedTask = taskService.updateTask(taskId, updatedDetails);


        assertEquals(updatedDetails.getTitle(), updatedTask.getTitle());
        assertEquals(updatedDetails.getDescription(), updatedTask.getDescription());
        verify(taskRepository).findById(taskId);
        verify(taskRepository).save(existingTask);
    }

    @Test
    void updateTask_ShouldThrowException_WhenTaskDoesNotExist() {

        Long taskId = 1L;
        Task updatedDetails = new Task();
        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());


        assertThrows(RuntimeException.class, () -> taskService.updateTask(taskId, updatedDetails));
        verify(taskRepository).findById(taskId);
    }

    @Test
    void deleteTask_ShouldDeleteTask_WhenTaskExists() {

        Long taskId = 1L;
        when(taskRepository.existsById(taskId)).thenReturn(true);


        taskService.deleteTask(taskId);


        verify(taskRepository).deleteById(taskId);
    }

    @Test
    void deleteTask_ShouldThrowException_WhenTaskDoesNotExist() {

        Long taskId = 1L;
        when(taskRepository.existsById(taskId)).thenReturn(false);


        assertThrows(RuntimeException.class, () -> taskService.deleteTask(taskId));
        verify(taskRepository).existsById(taskId);
    }
}