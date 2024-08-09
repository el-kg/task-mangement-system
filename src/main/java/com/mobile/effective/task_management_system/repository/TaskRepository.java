package com.mobile.effective.task_management_system.repository;

import com.mobile.effective.task_management_system.model.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing {@link Task} entities.
 */
public interface TaskRepository extends JpaRepository<Task, Long> {

    /**
     * Finds tasks created by the specified author ID with pagination.
     *
     * @param authorId the ID of the author who created the tasks
     * @param pageable the pagination information to retrieve tasks in a paginated format
     * @return a page of tasks created by the specified author
     */
    Page<Task> findByAuthorId(Long authorId, Pageable pageable);

    /**
     * Finds tasks assigned to the specified assignee ID with pagination.
     *
     * @param assigneeId the ID of the assignee to whom tasks are assigned
     * @param pageable   the pagination information to retrieve tasks in a paginated format
     * @return a page of tasks assigned to the specified assignee
     */
    Page<Task> findByAssigneeId(Long assigneeId, Pageable pageable);
}
