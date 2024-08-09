package com.mobile.effective.task_management_system.service.impl;

import com.mobile.effective.task_management_system.model.Task;
import com.mobile.effective.task_management_system.repository.TaskRepository;
import com.mobile.effective.task_management_system.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Implementation of the TaskService interface.
 */
@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    /**
     * Creates a new task.
     *
     * @param task the task to create
     * @return the created task
     */
    @Override
    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    /**
     * Retrieves tasks by the given author ID with pagination.
     *
     * @param authorId the ID of the author
     * @param pageable the pagination information
     * @return a page of tasks created by the author
     */
    @Override
    public Page<Task> getTasksByAuthorId(Long authorId, Pageable pageable) {
        return taskRepository.findByAuthorId(authorId, pageable);
    }

    /**
     * Retrieves tasks by the given assignee ID with pagination.
     *
     * @param assigneeId the ID of the assignee
     * @param pageable   the pagination information
     * @return a page of tasks assigned to the assignee
     */
    @Override
    public Page<Task> getTasksByAssigneeId(Long assigneeId, Pageable pageable) {
        return taskRepository.findByAssigneeId(assigneeId, pageable);
    }

    /**
     * Retrieves a task by its ID.
     *
     * @param taskId the ID of the task
     * @return an Optional containing the task if found, or empty if not found
     */
    @Override
    public Optional<Task> getTaskById(Long taskId) {
        return taskRepository.findById(taskId);
    }

    /**
     * Updates an existing task.
     *
     * @param taskId      the ID of the task to update
     * @param taskDetails the new details of the task
     * @return the updated task
     */
    @Override
    public Task updateTask(Long taskId, Task taskDetails) {
        return taskRepository.findById(taskId).map(task -> {
            task.setTitle(taskDetails.getTitle());
            task.setDescription(taskDetails.getDescription());
            task.setStatus(taskDetails.getStatus());
            task.setPriority(taskDetails.getPriority());
            task.setAssignee(taskDetails.getAssignee());
            return taskRepository.save(task);
        }).orElseThrow(() -> new RuntimeException("Task not found with id " + taskId));
    }

    /**
     * Deletes a task by its ID.
     *
     * @param taskId the ID of the task to delete
     */
    @Override
    public void deleteTask(Long taskId) {
        if (taskRepository.existsById(taskId)) {
            taskRepository.deleteById(taskId);
        } else {
            throw new RuntimeException("Task not found with id " + taskId);
        }
    }
}
