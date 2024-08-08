package com.mobile.effective.task_management_system.service;

import com.mobile.effective.task_management_system.model.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service interface for managing tasks.
 */
public interface TaskService {

    /**
     * Creates a new task.
     *
     * @param task the task to create
     * @return the created task
     */
    Task createTask(Task task);

    /**
     * Retrieves tasks by the given author ID with pagination.
     *
     * @param authorId the ID of the author
     * @param pageable the pagination information
     * @return a page of tasks created by the author
     */
    Page<Task> getTasksByAuthorId(Long authorId, Pageable pageable);

    /**
     * Retrieves tasks by the given assignee ID with pagination.
     *
     * @param assigneeId the ID of the assignee
     * @param pageable the pagination information
     * @return a page of tasks assigned to the assignee
     */
    Page<Task> getTasksByAssigneeId(Long assigneeId, Pageable pageable);

    /**
     * Retrieves a task by its ID.
     *
     * @param taskId the ID of the task
     * @return an Optional containing the task if found, or empty if not found
     */
    Optional<Task> getTaskById(Long taskId);

    /**
     * Updates an existing task.
     *
     * @param taskId the ID of the task to update
     * @param taskDetails the new details of the task
     * @return the updated task
     */
    Task updateTask(Long taskId, Task taskDetails);

    /**
     * Deletes a task by its ID.
     *
     * @param taskId the ID of the task to delete
     */
    void deleteTask(Long taskId);
}
