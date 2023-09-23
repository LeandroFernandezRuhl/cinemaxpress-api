package com.example.cinematicketingsystem.controller;

import com.example.cinematicketingsystem.security.dto.RegisterDTO;
import com.example.cinematicketingsystem.security.jwt.JwtUtils;
import com.example.cinematicketingsystem.security.model.Role;
import com.example.cinematicketingsystem.security.model.UserEntity;
import com.example.cinematicketingsystem.security.repository.RoleRepository;
import com.example.cinematicketingsystem.security.repository.UserRepository;
import com.example.cinematicketingsystem.security.model.CustomUserDetails;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.net.URI;
import java.util.Collections;

/**
 * Controller class for managing authentication and user registration.
 * <p>
 * This class handles HTTP requests related to user authentication and registration. It provides
 * endpoints for generating JWT cookies, authenticating demo users, and registering new users.
 * The endpoints are prefixed with "/auth".
 */
@RequiredArgsConstructor
@Slf4j
@Hidden
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    // Values from configuration properties.
    @Value("${cinemaxpress.app.demoUsername}")
    private String DEMO_USERNAME;
    @Value("${cinemaxpress.app.demoPassword}")
    private String DEMO_PASSWORD;
    @Value("${cinemaxpress.app.swaggerUiUrl}")
    private String SWAGGER_UI_URL;

    /**
     * Generates a JWT (JSON Web Token) cookie for an authenticated user.
     *
     * @param request  HttpServletRequest to access the request.
     * @param response HttpServletResponse to set the JWT cookie.
     * @return ResponseEntity with HTTP status and redirection to a location.
     */
    @PostMapping("/jwt-cookie")
    public ResponseEntity<?> generateJwtCookie(HttpServletRequest request, HttpServletResponse response) {
        // Check if the user is authenticated
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            // User is authenticated, generate and set the JWT cookie
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            ResponseCookie jwtCookie = jwtUtils.createJwtCookie(userDetails.getUsername());

            URI location = URI.create(SWAGGER_UI_URL);

            return ResponseEntity.status(HttpStatus.SEE_OTHER)
                    .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                    .header(HttpHeaders.LOCATION, location.toString())
                    .build();
        } else {
            // User is not authenticated, return an error response or redirect to the login page
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed");
        }
    }

    /**
     * Authenticates a demo user and generates a JWT cookie.
     *
     * @param request  HttpServletRequest to access the request.
     * @param response HttpServletResponse to set the JWT cookie.
     * @return ResponseEntity with HTTP status and redirection to a location.
     */
    @GetMapping("/demo-user")
    public ResponseEntity<?> getDemoUser(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(DEMO_USERNAME, DEMO_PASSWORD));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        ResponseCookie jwtCookie = jwtUtils.createJwtCookie(userDetails.getUsername());

        URI location = URI.create(SWAGGER_UI_URL);

        return ResponseEntity.status(HttpStatus.SEE_OTHER)
                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .header(HttpHeaders.LOCATION, location.toString())
                .build();
    }

    /**
     * Handles user registration based on the provided registration data.
     *
     * @param registerDTO {@link RegisterDTO} Registration data received as a form.
     * @return RedirectView to a login page or an error page.
     */
    @PostMapping("/register")
    public RedirectView register(@ModelAttribute RegisterDTO registerDTO) {
        if (userRepository.existsByUsername(registerDTO.getUsername())) {
            return new RedirectView("/register?error=UsernameTaken");
        }

        /*if (userRepository.existsByEmail(registerDTO.getEmail())) {
            return ResponseEntity.badRequest().body("Error: Email is already taken!");
        } */

        UserEntity user = new UserEntity();
        user.setUsername(registerDTO.getUsername());
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));

        Role roles = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        user.setRoles(Collections.singletonList(roles));
        userRepository.save(user);

        return new RedirectView("/login");
    }

    /**
     * Handles user registration for administrators.
     *
     * @param registerDTO {@link RegisterDTO} Registration data received as JSON.
     * @return ResponseEntity with a success message or an error response.
     */
    @PostMapping("/register-admin")
    public ResponseEntity<String> registerAdmin(@RequestBody RegisterDTO registerDTO) {
        if (Boolean.TRUE.equals(userRepository.existsByUsername(registerDTO.getUsername()))) {
            return ResponseEntity.badRequest().body("Error: Username is already taken!");
        }

        UserEntity user = new UserEntity();
        user.setUsername(registerDTO.getUsername());
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));

        Role roles = roleRepository.findByName("ROLE_ADMIN")
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        user.setRoles(Collections.singletonList(roles));
        userRepository.save(user);

        return ResponseEntity.ok("User registered successfully!");
    }
}
