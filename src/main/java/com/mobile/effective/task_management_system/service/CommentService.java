package com.mobile.effective.task_management_system.service;

import com.mobile.effective.task_management_system.model.Comment;

import java.util.List;

/**
 * Service interface for managing comments.
 */
public interface CommentService {

    /**
     * Adds a new comment.
     *
     * @param comment the comment to add
     * @return the added comment
     */
    Comment addComment(Comment comment);

    /**
     * Retrieves comments by the given task ID.
     *
     * @param taskId the ID of the task
     * @return a list of comments associated with the task
     */
    List<Comment> getCommentByTaskId(Long taskId);
}
