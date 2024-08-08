package com.mobile.effective.task_management_system.repository;

import com.mobile.effective.task_management_system.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository interface for managing {@link Comment} entities.
 */
public interface CommentRepository extends JpaRepository<Comment, Long> {

    /**
     * Finds all comments associated with the specified task ID.
     *
     * @param taskId the ID of the task
     * @return a list of comments related to the task
     */
    List<Comment> findByTaskId(Long taskId);
}

