package com.mobile.effective.task_management_system.controller;

import com.mobile.effective.task_management_system.model.User;
import com.mobile.effective.task_management_system.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Controller for managing users.
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    /**
     * Constructs a UserController with the specified UserService.
     *
     * @param userService the user service to use
     */
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Registers a new user.
     *
     * @param user the user to register
     * @return the registered user
     */
    @Operation(summary = "Регистрирует нового пользователя", description = "Регистрирует нового пользователя с указанными данными")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователь успешно зарегистрирован"),
            @ApiResponse(responseCode = "400", description = "Ошибка валидации данных")
    })
    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody @Valid User user) {
        User registeredUser = userService.registerUser(user.getEmail(), user.getPassword(), user.getName());
        return ResponseEntity.ok(registeredUser);
    }

    /**
     * Retrieves a user by their email.
     *
     * @param email the email of the user
     * @return the user with the given email
     */
    @Operation(summary = "Получает пользователя по email", description = "Возвращает информацию о пользователе с указанным email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователь успешно найден"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден")
    })
    @GetMapping("/{email}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<User> getUserByEmail(
            @Parameter(description = "Email пользователя") @PathVariable String email) {
        Optional<User> user = userService.findByEmail(email);
        return user.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
