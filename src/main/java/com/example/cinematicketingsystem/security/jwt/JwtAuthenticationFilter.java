package com.example.cinematicketingsystem.security.jwt;

import com.example.cinematicketingsystem.security.service.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
/**
 * JWT (JSON Web Token) authentication filter for securing API endpoints.
 * <p>
 * This class extends {@link OncePerRequestFilter} and is responsible for processing JWT tokens from incoming requests.
 * It validates the JWT token, loads user details, and sets the user's authentication in the security context.
 */
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private CustomUserDetailsService userDetailsService;

    /**
     * Processes incoming HTTP requests to authenticate users using JWT tokens.
     *
     * @param request     The HTTP request.
     * @param response    The HTTP response.
     * @param filterChain The filter chain.
     * @throws ServletException if there is an error during request processing.
     * @throws IOException      if there is an I/O error.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            // Get JWT token from cookies in the request.
            String jwt = jwtUtils.getJwtFromCookies(request);
            if (jwt != null) {
                // Validate the JWT token.
                JwtValidationResult validationResult = jwtUtils.validateJwtToken(jwt);
                if (validationResult == JwtValidationResult.VALID) {
                    // Extract the username from the JWT token.
                    String username = jwtUtils.getUsernameFromJwtToken(jwt);

                    // Load user details based on the username.
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                    // Create an authentication token and set it in the security context.
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(userDetails,
                                    null,
                                    userDetails.getAuthorities());

                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);

                    log.info("User's JWT validated successfully");
                }
            }
        } catch (Exception e) {
            log.error("Cannot set user authentication: " + e.getMessage());
        }
        filterChain.doFilter(request, response);
    }
}
