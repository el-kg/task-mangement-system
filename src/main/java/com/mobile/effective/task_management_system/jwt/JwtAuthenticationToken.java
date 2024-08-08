package com.mobile.effective.task_management_system.jwt;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * Custom implementation of {@link AbstractAuthenticationToken} for JWT-based authentication.
 */
public class JwtAuthenticationToken extends AbstractAuthenticationToken {

    private final UserDetails userDetails;

    /**
     * Constructs a JwtAuthenticationToken with the specified user details and authorities.
     *
     * @param userDetails the user details
     * @param authorities the authorities granted to the user
     */
    public JwtAuthenticationToken(UserDetails userDetails, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.userDetails = userDetails;
        setAuthenticated(true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object getCredentials() {
        // Credentials are not used in this implementation as they are provided by the JWT token
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object getPrincipal() {
        return userDetails;
    }
}
