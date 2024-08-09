package com.mobile.effective.task_management_system.jwt;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * Custom implementation of {@link AbstractAuthenticationToken} for JWT-based authentication.
 * This token holds the user details and authorities granted to the user.
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
     * Credentials are not used in this implementation as they are provided by the JWT token.
     */
    @Override
    public Object getCredentials() {
        return null;
    }

    /**
     * {@inheritDoc}
     * Returns the user details associated with this authentication token.
     *
     * @return the user details
     */
    @Override
    public Object getPrincipal() {
        return userDetails;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        JwtAuthenticationToken that = (JwtAuthenticationToken) obj;
        return userDetails.equals(that.userDetails);
    }

    @Override
    public int hashCode() {
        return userDetails.hashCode();
    }
}
