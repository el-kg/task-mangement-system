package com.mobile.effective.task_management_system.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

/**
 * Represents a user in the task management system.
 * This entity holds information about a user, including their email, password, and name.
 */
@Entity
@Getter
@Setter
@Table(name = "\"user\"")
@Schema(description = "Модель пользователя")
public class User {

    /**
     * Unique identifier for the user.
     * This field is auto-generated and serves as the primary key for the user entity.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID пользователя", example = "1")
    private Long id;

    /**
     * Email of the user, must be unique and not empty.
     * The email should follow the standard email format.
     */
    @Email(message = "Email должен быть корректным")
    @NotEmpty(message = "Email не может быть пустым")
    @Column(unique = true, nullable = false)
    @Schema(description = "Email пользователя", example = "user@example.com")
    private String email;

    /**
     * Password of the user.
     * The password must be at least 6 characters long.
     */
    @Size(min = 6, message = "Пароль должен содержать не менее 6 символов")
    @Column(nullable = false)
    @Schema(description = "Пароль пользователя", example = "password123")
    private String password;

    /**
     * Name of the user.
     * The name cannot be empty.
     */
    @NotEmpty(message = "Имя не может быть пустым")
    @Column(nullable = false)
    @Schema(description = "Имя пользователя", example = "Иван Иванов")
    private String name;
}
