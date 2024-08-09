package com.mobile.effective.task_management_system.config;

import com.mobile.effective.task_management_system.jwt.JwtTokenFilter;
import com.mobile.effective.task_management_system.jwt.JwtTokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

/**
 * Security configuration class for the application.
 * This class configures security settings, including authentication, authorization rules,
 * and integrates the JWT token filter into the security filter chain.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;

    /**
     * Constructs an instance of SecurityConfig.
     *
     * @param jwtTokenProvider the JWT token provider used for authentication
     */
    public SecurityConfig(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    /**
     * Provides a PasswordEncoder bean to encode passwords using BCrypt.
     *
     * @return a PasswordEncoder instance configured with BCrypt
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configures the security filter chain for the application.
     * Sets up authorization rules, disables CSRF protection, and adds the JWT token filter
     * to the security filter chain before the UsernamePasswordAuthenticationFilter.
     *
     * @param http the HttpSecurity instance used to configure security settings
     * @return the configured SecurityFilterChain
     * @throws Exception in case of an error during configuration
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        JwtTokenFilter jwtTokenFilter = new JwtTokenFilter(jwtTokenProvider);

        http
                .csrf().disable()  // Disable CSRF protection for simplicity
                .authorizeRequests()
                .requestMatchers("/api/auth/**").permitAll()  // Permit all requests to authentication endpoints
                // Allow access to Swagger UI and related resources
                .requestMatchers("/swagger-ui.html", "/api/tasks/**", "/api/comments/**", "/api/users/**", "/v3/api-docs/**", "/swagger-resources/**", "/webjars/**", "/swagger-ui/**").permitAll()
                .anyRequest().authenticated()  // Require authentication for all other requests
                .and()
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);  // Add JWT filter before standard authentication filter

        return http.build();
    }

    /**
     * Provides an AuthenticationManager bean for authentication operations.
     *
     * @param authenticationConfiguration the AuthenticationConfiguration used to create the AuthenticationManager
     * @return the AuthenticationManager instance
     * @throws Exception in case of an error during the creation of the AuthenticationManager
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * Configures Cross-Origin Resource Sharing (CORS) settings for the application.
     * <p>
     * This bean defines the CORS configuration to allow requests from any origin (`*`),
     * supports the HTTP methods GET, POST, PUT, and DELETE, and permits headers
     * `Authorization` and `Content-Type` in requests. It sets up CORS for all URL paths
     * by registering the configuration with a {@link UrlBasedCorsConfigurationSource}.
     * </p>
     *
     * @return a {@link CorsConfigurationSource} instance that contains the CORS configuration
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*")); // Или указать точные домены
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}

