package com.mobile.effective.task_management_system.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

import java.io.IOException;

/**
 * A filter that handles authentication based on JWT tokens.
 * This filter checks for the presence and validity of a JWT token in requests and sets the authentication in the security context.
 */
public class JwtTokenFilter implements Filter {

    private final JwtTokenProvider jwtTokenProvider;

    /**
     * Constructor for creating an instance of the JWT token filter.
     *
     * @param jwtTokenProvider the JWT token provider used for token operations
     */
    public JwtTokenFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Initialize resources if needed
    }

    /**
     * Filters the incoming request to check for a valid JWT token.
     *
     * @param request the HTTP request
     * @param response the HTTP response
     * @param chain the filter chain
     * @throws IOException in case of an input/output error
     * @throws ServletException in case of a servlet error
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String path = httpRequest.getRequestURI();

        // Логирование для отладки
        System.out.println("Request URI: " + path);

        // Пропускаем запросы к Swagger UI и связанным ресурсам
        if (path.startsWith("/swagger-ui.html") ||
                path.startsWith("/v3/api-docs/") ||
                path.startsWith("/swagger-resources/") ||
                path.startsWith("/webjars/")) {
            System.out.println("Allowing access to Swagger UI resources");
            chain.doFilter(request, response);
            return;
        }

        String token = jwtTokenProvider.resolveToken(httpRequest);
        if (token != null && jwtTokenProvider.validateToken(token)) {
            Authentication authentication = jwtTokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // Clean up resources if needed
    }
}
