package com.mobile.effective.task_management_system.service;

import com.mobile.effective.task_management_system.model.User;

import java.util.Optional;

/**
 * Service interface for managing users.
 */
public interface UserService {

    /**
     * Registers a new user with the given email, password, and name.
     *
     * @param email the email of the user
     * @param password the password of the user
     * @param name the name of the user
     * @return the registered user
     */
    User registerUser(String email, String password, String name);

    /**
     * Finds a user by email.
     *
     * @param email the email of the user
     * @return an Optional containing the user if found, or empty if not found
     */
    Optional<User> findByEmail(String email);
}