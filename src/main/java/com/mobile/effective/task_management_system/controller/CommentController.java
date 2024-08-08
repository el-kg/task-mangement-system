package com.mobile.effective.task_management_system.controller;

import com.mobile.effective.task_management_system.model.Comment;
import com.mobile.effective.task_management_system.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for managing comments.
 */
@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    /**
     * Adds a new comment.
     *
     * @param comment the comment to add
     * @return the added comment
     */
    @Operation(summary = "Добавляет новый комментарий", description = "Создает новый комментарий и возвращает его")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Комментарий успешно добавлен"),
            @ApiResponse(responseCode = "400", description = "Ошибка валидации данных")
    })
    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Comment> addComment(
            @Parameter(description = "Комментарий, который нужно добавить") @RequestBody @Valid Comment comment) {
        Comment createdComment = commentService.addComment(comment);
        return ResponseEntity.ok(createdComment);
    }

    /**
     * Retrieves comments by the given task ID.
     *
     * @param taskId the ID of the task
     * @return a list of comments associated with the task
     */
    @Operation(summary = "Получает комментарии по ID задачи", description = "Возвращает список комментариев для указанной задачи")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список комментариев успешно получен"),
            @ApiResponse(responseCode = "400", description = "Неверный ID задачи")
    })
    @GetMapping("/task/{taskId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<Comment>> getCommentsByTaskId(
            @Parameter(description = "ID задачи для получения комментариев") @PathVariable Long taskId) {
        List<Comment> comments = commentService.getCommentByTaskId(taskId);
        return ResponseEntity.ok(comments);
    }
}

