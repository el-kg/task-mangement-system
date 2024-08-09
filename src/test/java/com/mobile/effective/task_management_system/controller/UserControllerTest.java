package com.mobile.effective.task_management_system.controller;



import com.mobile.effective.task_management_system.model.User;
import com.mobile.effective.task_management_system.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void testRegisterUserSuccess() throws Exception {
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("password");
        user.setName("Test User");

        when(userService.registerUser("test@example.com", "password", "Test User")).thenReturn(user);

        mockMvc.perform(post("/api/users/register")
                        .contentType("application/json")
                        .content("{\"email\":\"test@example.com\",\"password\":\"password\",\"name\":\"Test User\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("test@example.com"))
                .andExpect(jsonPath("$.name").value("Test User"));

        verify(userService, times(1)).registerUser("test@example.com", "password", "Test User");
    }

    @Test
    void testRegisterUserValidationError() throws Exception {
        mockMvc.perform(post("/api/users/register")
                        .contentType("application/json")
                        .content("{\"email\":\"\",\"password\":\"\",\"name\":\"\"}"))
                .andExpect(status().isBadRequest());  // Ожидаем ошибку 400

        verify(userService, times(0)).registerUser(anyString(), anyString(), anyString());
    }

    @Test
    @WithMockUser
    void testGetUserByEmailSuccess() throws Exception {
        User user = new User();
        user.setEmail("test@example.com");
        user.setName("Test User");

        when(userService.findByEmail("test@example.com")).thenReturn(Optional.of(user));

        mockMvc.perform(get("/api/users/{email}", "test@example.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("test@example.com"))
                .andExpect(jsonPath("$.name").value("Test User"));

        verify(userService, times(1)).findByEmail("test@example.com");
    }

    @Test
    @WithMockUser
    void testGetUserByEmailNotFound() throws Exception {
        when(userService.findByEmail("nonexistent@example.com")).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/users/{email}", "nonexistent@example.com"))
                .andExpect(status().isNotFound());

        verify(userService, times(1)).findByEmail("nonexistent@example.com");
    }
}