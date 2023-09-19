package com.example.cinematicketingsystem.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Custom entry point for handling unauthorized access.
 * <p>
 * This class implements the {@link AuthenticationEntryPoint} interface and is responsible for handling
 * unauthorized access by returning an appropriate response when authentication fails.
 */
@Slf4j
@Component
public class JwtAuthEntryPoint implements AuthenticationEntryPoint {

    /**
     * Handles unauthorized access by sending an unauthorized response.
     *
     * @param request       The HTTP request.
     * @param response      The HTTP response.
     * @param authException The authentication exception that occurred.
     * @throws IOException      if there is an I/O error.
     * @throws ServletException if there is a servlet-related error.
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {
        log.error("Unauthorized error: {}", authException.getMessage());

        // Set the response content type to JSON.
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        // Set the HTTP status code to Unauthorized.
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        // Create a JSON response body with error details.
        final Map<String, Object> body = new HashMap<>();
        body.put("status", HttpServletResponse.SC_UNAUTHORIZED);
        body.put("error", "Unauthorized");
        body.put("message", authException.getMessage());
        body.put("path", request.getServletPath());

        // Serialize the JSON response body and write it to the response output stream.
        final ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), body);
    }
}
