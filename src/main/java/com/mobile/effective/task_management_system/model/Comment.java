package com.mobile.effective.task_management_system.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Represents a comment on a task in the task management system.
 */
@Entity
@Getter
@Setter
@Schema(description = "Модель комментария")
public class Comment {

    /**
     * Unique identifier for the comment.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID комментария", example = "1")
    private Long id;

    /**
     * Content of the comment.
     */
    @Column(nullable = false)
    @Schema(description = "Содержание комментария", example = "Это комментарий.")
    private String content;

    /**
     * The task to which this comment belongs.
     */
    @ManyToOne
    @JoinColumn(name = "task_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_comment_task"))
    @Schema(description = "Задача, к которой принадлежит комментарий")
    private Task task;

    /**
     * The user who authored the comment.
     */
    @ManyToOne
    @JoinColumn(name = "author_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_comment_author"))
    @Schema(description = "Автор комментария")
    private User author;
}
