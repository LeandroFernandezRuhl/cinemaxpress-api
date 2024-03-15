package com.leandroruhl.cinemaxpress.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;
import java.security.Key;
import java.util.Date;

/**
 * Utility class for working with JSON Web Tokens (JWTs) and cookies.
 * <p>
 * This class provides methods for generating, validating, and manipulating JWT tokens,
 * as well as creating and retrieving cookies related to JWTs.
 */
@Slf4j
@Component
public class JwtUtils {
    @Value("${cinemaxpress.app.jwtSecret}")
    private String JWT_SECRET;
    @Value("${cinemaxpress.app.jwtExpirationMs}")
    private int JWT_EXPIRATION_MS;
    @Value("${cinemaxpress.app.cookieMaxAgeSec}")
    private int COOKIE_MAX_AGE_SEC;
    @Value("${cinemaxpress.app.jwtCookieName}")
    private String JWT_COOKIE_NAME;

    /**
     * Generates a JWT token from a username.
     *
     * @param username The username to include in the token.
     * @return The generated JWT token.
     */
    public String generateTokenFromUsername(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + JWT_EXPIRATION_MS))
                .signWith(key(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Creates a JWT cookie for the provided username.
     *
     * @param username The username to include in the token.
     * @return The JWT {@link ResponseCookie}.
     */
    public ResponseCookie createJwtCookie(String username) {
        String jwt = generateTokenFromUsername(username);
        return generateCookie(JWT_COOKIE_NAME, jwt, "/");
    }

    /**
     * Generates a ResponseCookie with the specified name, value, and path.
     *
     * @param name  The name of the cookie.
     * @param value The value of the cookie.
     * @param path  The path for the cookie.
     * @return The generated {@link ResponseCookie}.
     */
    private ResponseCookie generateCookie(String name, String value, String path) {
        return ResponseCookie.from(name, value).path(path).maxAge(COOKIE_MAX_AGE_SEC).httpOnly(true).build();
    }

    /**
     * Retrieves the key used for signing JWTs.
     *
     * @return The JWT signing {@link Key}.
     */
    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(JWT_SECRET));
    }

    /**
     * Validates a JWT token and returns a result indicating its validity.
     *
     * @param authToken The JWT token to validate.
     * @return The {@link JwtValidationResult}.
     */
    public JwtValidationResult validateJwtToken(String authToken) {
        try {
            Jwts.parserBuilder().setSigningKey(key()).build().parseClaimsJws(authToken);
            return JwtValidationResult.VALID;
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
            return JwtValidationResult.INVALID;
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
            return JwtValidationResult.EXPIRED;
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
            return JwtValidationResult.UNSUPPORTED;
        } catch (IllegalArgumentException e) {
            log.error("JWT is null or claims string is empty: {}", e.getMessage());
            return JwtValidationResult.EMPTY_CLAIMS;
        }
    }

    /**
     * Retrieves the username from a JWT token.
     *
     * @param token The JWT token.
     * @return The username extracted from the token.
     */
    public String getUsernameFromJwtToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key()).build()
                .parseClaimsJws(token).getBody().getSubject();
    }

    /**
     * Retrieves a JWT token from cookies in an HTTP request.
     *
     * @param request The HTTP request containing cookies.
     * @return The JWT token retrieved from cookies.
     */
    public String getJwtFromCookies(HttpServletRequest request) {
        return getCookieValueByName(request, JWT_COOKIE_NAME);
    }

    /**
     * Retrieves the value of a cookie by its name from an HTTP request.
     *
     * @param request The HTTP request containing cookies.
     * @param name    The name of the cookie to retrieve.
     * @return The value of the cookie, or null if the cookie is not found.
     */
    private String getCookieValueByName(HttpServletRequest request, String name) {
        Cookie cookie = WebUtils.getCookie(request, name);
        if (cookie != null) {
            return cookie.getValue();
        } else {
            return null;
        }
    }

    /**
     * Generates a clean (deleted) JWT cookie with the specified name and path.
     *
     * @return The clean JWT cookie.
     */
    public ResponseCookie getCleanJwtCookie() {
        return ResponseCookie.from(JWT_COOKIE_NAME, null).path("/").build();
    }
}
