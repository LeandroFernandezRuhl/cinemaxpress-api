package com.example.cinematicketingsystem.security.jwt;


/**
 * Enum representing the result of JWT (JSON Web Token) validation.
 * <p>
 * This enum defines possible outcomes when validating a JWT token, including whether it's valid,
 * expired, invalid, unsupported, or if its claims are empty.
 */
public enum JwtValidationResult {
    VALID,
    INVALID,
    EXPIRED,
    UNSUPPORTED,
    EMPTY_CLAIMS
}
