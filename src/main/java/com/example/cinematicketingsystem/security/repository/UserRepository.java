package com.example.cinematicketingsystem.security.repository;

import com.example.cinematicketingsystem.security.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for accessing {@link UserEntity} data in the database.
 * <p>
 * This repository extends {@link JpaRepository} to provide CRUD (Create, Read, Update, Delete) operations
 * for user entities in the database. It includes custom methods to find a user by username and
 * check if a user with a given username exists.
 */
@Repository // Indicates that this interface is a Spring Data repository.
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    /**
     * Finds a user by their username.
     *
     * @param username The username of the user to find.
     * @return An Optional containing the user if found, or an empty Optional if not found.
     */
    Optional<UserEntity> findByUsername(String username);

    /**
     * Checks if a user with the given username exists.
     *
     * @param username The username to check for existence.
     * @return True if a user with the given username exists, false otherwise.
     */
    Boolean existsByUsername(String username);
}
