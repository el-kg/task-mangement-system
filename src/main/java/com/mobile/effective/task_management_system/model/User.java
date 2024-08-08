package com.mobile.effective.task_management_system.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Represents a user in the task management system.
 */
@Entity
@Getter
@Setter
@Table(name = "\"user\"")
@Schema(description = "Модель пользователя")
public class User {

    /**
     * Unique identifier for the user.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID пользователя", example = "1")
    private Long id;

    /**
     * Email of the user, must be unique.
     */
    @Column(unique = true, nullable = false)
    @Schema(description = "Email пользователя", example = "user@example.com")
    private String email;

    /**
     * Password of the user.
     */
    @Column(nullable = false)
    @Schema(description = "Пароль пользователя", example = "password123")
    private String password;

    /**
     * Name of the user.
     */
    @Column(nullable = false)
    @Schema(description = "Имя пользователя", example = "Иван Иванов")
    private String name;
}
