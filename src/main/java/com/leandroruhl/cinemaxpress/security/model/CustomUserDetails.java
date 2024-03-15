package com.leandroruhl.cinemaxpress.security.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Objects;
/**
 * Custom implementation of the {@link UserDetails} interface for user authentication.
 * <p>
 * This class represents user details required for authentication and authorization. It implements
 * the UserDetails interface, providing methods for retrieving user information and security-related
 * attributes.
 */
@Getter
@Setter
public class CustomUserDetails implements UserDetails {
    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;
    private final Long id; // User ID.
    private final String username; // User's username.
    @JsonIgnore // Ignores the 'password' field when serializing to JSON.
    private final String password; // User's password.
    private final Collection<? extends GrantedAuthority> authorities; // User's roles and permissions.
    private final boolean accountNonExpired; // Indicates whether the account is non-expired.
    private final boolean accountNonLocked; // Indicates whether the account is non-locked.
    private final boolean credentialsNonExpired; // Indicates whether the credentials are non-expired.
    private final boolean enabled; // Indicates whether the account is enabled.

    /**
     * Constructs a CustomUserDetails object with the provided user details.
     *
     * @param id          The user's ID.
     * @param username    The user's username.
     * @param password    The user's password.
     * @param authorities The user's roles and permissions.
     */
    public CustomUserDetails(Long id, String username, String password, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.authorities = authorities;
        this.accountNonExpired = true;
        this.accountNonLocked = true;
        this.credentialsNonExpired = true;
        this.enabled = true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities; // Returns the user's roles and permissions.
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired; // Indicates whether the account is non-expired.
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked; // Indicates whether the account is non-locked.
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired; // Indicates whether the credentials are non-expired.
    }

    @Override
    public boolean isEnabled() {
        return enabled; // Indicates whether the account is enabled.
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomUserDetails that = (CustomUserDetails) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
