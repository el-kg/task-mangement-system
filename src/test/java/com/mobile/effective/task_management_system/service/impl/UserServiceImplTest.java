package com.mobile.effective.task_management_system.service.impl;

import com.mobile.effective.task_management_system.model.User;
import com.mobile.effective.task_management_system.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registerUser_ShouldReturnRegisteredUser() {

        String email = "test@example.com";
        String password = "password";
        String encodedPassword = "encodedPassword";
        String name = "Test User";

        User user = new User();
        user.setEmail(email);
        user.setPassword(encodedPassword);
        user.setName(name);

        when(passwordEncoder.encode(password)).thenReturn(encodedPassword);
        when(userRepository.save(any(User.class))).thenReturn(user);


        User registeredUser = userService.registerUser(email, password, name);


        assertEquals(email, registeredUser.getEmail());
        assertEquals(encodedPassword, registeredUser.getPassword());
        assertEquals(name, registeredUser.getName());
        verify(passwordEncoder).encode(password);
        verify(userRepository).save(any(User.class));
    }

    @Test
    void findByEmail_ShouldReturnUser_WhenUserExists() {

        String email = "test@example.com";
        User user = new User();
        user.setEmail(email);

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));


        Optional<User> foundUser = userService.findByEmail(email);


        assertTrue(foundUser.isPresent());
        assertEquals(email, foundUser.get().getEmail());
        verify(userRepository).findByEmail(email);
    }

    @Test
    void findByEmail_ShouldReturnEmpty_WhenUserDoesNotExist() {

        String email = "nonexistent@example.com";

        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());


        Optional<User> foundUser = userService.findByEmail(email);


        assertFalse(foundUser.isPresent());
        verify(userRepository).findByEmail(email);
    }

    @Test
    void loadUserByUsername_ShouldReturnUserDetails_WhenUserExists() {

        String email = "test@example.com";
        String password = "password";
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));


        UserDetails userDetails = userService.loadUserByUsername(email);


        assertEquals(email, userDetails.getUsername());
        assertEquals(password, userDetails.getPassword());
        assertTrue(userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_USER")));
        verify(userRepository).findByEmail(email);
    }

    @Test
    void loadUserByUsername_ShouldThrowUsernameNotFoundException_WhenUserDoesNotExist() {

        String email = "nonexistent@example.com";

        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());


        assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername(email));
        verify(userRepository).findByEmail(email);
    }
}