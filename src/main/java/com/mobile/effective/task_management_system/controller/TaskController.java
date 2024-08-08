package com.mobile.effective.task_management_system.controller;

import com.mobile.effective.task_management_system.model.Task;
import com.mobile.effective.task_management_system.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Controller for managing tasks.
 */
@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    /**
     * Creates a new task.
     *
     * @param task the task to create
     * @return the created task
     */
    @Operation(summary = "Создает новую задачу", description = "Создает новую задачу с указанными данными")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Задача успешно создана"),
            @ApiResponse(responseCode = "400", description = "Ошибка валидации данных")
    })
    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Task> createTask(@RequestBody @Valid Task task) {
        Task createdTask = taskService.createTask(task);
        return ResponseEntity.ok(createdTask);
    }

    /**
     * Retrieves tasks by the given author ID with pagination.
     *
     * @param authorId the ID of the author
     * @param pageable the pagination information
     * @return a page of tasks created by the author
     */
    @Operation(summary = "Получает задачи по ID автора", description = "Возвращает список задач, созданных указанным автором")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список задач успешно получен"),
            @ApiResponse(responseCode = "400", description = "Неверный ID автора")
    })
    @GetMapping("/author/{authorId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Page<Task>> getTasksByAuthorId(
            @Parameter(description = "ID автора задач") @PathVariable Long authorId,
            @Parameter(description = "Параметры для пагинации") Pageable pageable) {
        Page<Task> tasks = taskService.getTasksByAuthorId(authorId, pageable);
        return ResponseEntity.ok(tasks);
    }

    /**
     * Retrieves tasks by the given assignee ID with pagination.
     *
     * @param assigneeId the ID of the assignee
     * @param pageable the pagination information
     * @return a page of tasks assigned to the assignee
     */
    @Operation(summary = "Получает задачи по ID исполнителя", description = "Возвращает список задач, назначенных указанному исполнителю")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список задач успешно получен"),
            @ApiResponse(responseCode = "400", description = "Неверный ID исполнителя")
    })
    @GetMapping("/assignee/{assigneeId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Page<Task>> getTasksByAssigneeId(
            @Parameter(description = "ID исполнителя задач") @PathVariable Long assigneeId,
            @Parameter(description = "Параметры для пагинации") Pageable pageable) {
        Page<Task> tasks = taskService.getTasksByAssigneeId(assigneeId, pageable);
        return ResponseEntity.ok(tasks);
    }

    /**
     * Retrieves a task by its ID.
     *
     * @param taskId the ID of the task
     * @return the task with the given ID
     */
    @Operation(summary = "Получает задачу по ID", description = "Возвращает задачу с указанным ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Задача успешно найдена"),
            @ApiResponse(responseCode = "404", description = "Задача не найдена")
    })
    @GetMapping("/{taskId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Task> getTaskById(
            @Parameter(description = "ID задачи") @PathVariable Long taskId) {
        Optional<Task> task = taskService.getTaskById(taskId);
        return task.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Updates an existing task.
     *
     * @param taskId the ID of the task to update
     * @param taskDetails the new details of the task
     * @return the updated task
     */
    @Operation(summary = "Обновляет задачу", description = "Обновляет существующую задачу с указанным ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Задача успешно обновлена"),
            @ApiResponse(responseCode = "400", description = "Ошибка валидации данных"),
            @ApiResponse(responseCode = "404", description = "Задача не найдена")
    })
    @PutMapping("/{taskId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Task> updateTask(
            @Parameter(description = "ID задачи для обновления") @PathVariable Long taskId,
            @RequestBody @Valid Task taskDetails) {
        Task updatedTask = taskService.updateTask(taskId, taskDetails);
        return ResponseEntity.ok(updatedTask);
    }

    /**
     * Deletes a task by its ID.
     *
     * @param taskId the ID of the task to delete
     * @return a response indicating the outcome of the delete operation
     */
    @Operation(summary = "Удаляет задачу по ID", description = "Удаляет задачу с указанным ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Задача успешно удалена"),
            @ApiResponse(responseCode = "404", description = "Задача не найдена")
    })
    @DeleteMapping("/{taskId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> deleteTask(
            @Parameter(description = "ID задачи для удаления") @PathVariable Long taskId) {
        taskService.deleteTask(taskId);
        return ResponseEntity.noContent().build();
    }
}
