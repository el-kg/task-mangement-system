package com.mobile.effective.task_management_system.repository;

import com.mobile.effective.task_management_system.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository interface for managing {@link User} entities.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Finds a user by their email address.
     *
     * @param email the email address of the user
     * @return an Optional containing the user if found, or an empty Optional if no user is found
     */
    Optional<User> findByEmail(String email);
}
