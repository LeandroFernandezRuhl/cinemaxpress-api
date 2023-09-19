package com.example.cinematicketingsystem.security.service;

import com.example.cinematicketingsystem.security.model.CustomUserDetails;
import com.example.cinematicketingsystem.security.model.Role;
import com.example.cinematicketingsystem.security.model.UserEntity;
import com.example.cinematicketingsystem.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Custom service for loading user details during authentication.
 * <p>
 * This service implements the {@link UserDetailsService} interface to load user details during the authentication process.
 * It retrieves user information from the UserRepository and maps user roles to authorities.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository; // Repository for accessing user data.

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Loads user details by username.
     *
     * @param username The username to load user details for.
     * @return A UserDetails object representing the user.
     * @throws UsernameNotFoundException if the user is not found.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Retrieve user details from the UserRepository.
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        // Create and return a CustomUserDetails object with user information.
        return new CustomUserDetails(user.getId(), user.getUsername(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }

    /**
     * Maps user roles to authorities.
     *
     * @param roles The list of roles assigned to the user.
     * @return A collection of authorities representing user roles.
     */
    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(List<Role> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName())) // Map role names to authorities.
                .collect(Collectors.toList());
    }
}
