package com.mobile.effective.task_management_system.service.impl;

import com.mobile.effective.task_management_system.model.Comment;
import com.mobile.effective.task_management_system.repository.CommentRepository;
import com.mobile.effective.task_management_system.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of the CommentService interface.
 */
@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    /**
     * Adds a new comment.
     *
     * @param comment the comment to add
     * @return the added comment
     */
    @Override
    public Comment addComment(Comment comment) {
        return commentRepository.save(comment);
    }

    /**
     * Retrieves comments by the given task ID.
     *
     * @param taskId the ID of the task
     * @return a list of comments associated with the task
     */
    @Override
    public List<Comment> getCommentByTaskId(Long taskId) {
        return commentRepository.findByTaskId(taskId);
    }
}
