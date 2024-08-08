package com.mobile.effective.task_management_system.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Represents a task in the task management system.
 */
@Entity
@Getter
@Setter
@Schema(description = "Модель задачи")
public class Task {

    /**
     * Unique identifier for the task.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID задачи", example = "1")
    private Long id;

    /**
     * Title of the task.
     */
    @Column(nullable = false)
    @Schema(description = "Заголовок задачи", example = "Задача №1")
    private String title;

    /**
     * Description of the task.
     */
    @Schema(description = "Описание задачи", example = "Описание задачи №1")
    private String description;

    /**
     * Status of the task, e.g., "pending", "in progress", "completed".
     */
    @Column(nullable = false)
    @Schema(description = "Статус задачи", example = "В ожидании")
    private String status;

    /**
     * Priority of the task, e.g., "high", "medium", "low".
     */
    @Column(nullable = false)
    @Schema(description = "Приоритет задачи", example = "Высокий")
    private String priority;

    /**
     * The user who created the task.
     */
    @ManyToOne
    @JoinColumn(name = "author_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_task_author"))
    @Schema(description = "Автор задачи")
    private User author;

    /**
     * The user who is assigned to the task.
     */
    @ManyToOne
    @JoinColumn(name = "assignee_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_task_assignee"))
    @Schema(description = "Исполнитель задачи")
    private User assignee;
}
