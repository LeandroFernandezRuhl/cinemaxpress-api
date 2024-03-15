package com.leandroruhl.cinemaxpress.security.repository;

import com.leandroruhl.cinemaxpress.security.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for accessing {@link Role} data in the database.
 * <p>
 * This repository extends {@link JpaRepository} to provide CRUD (Create, Read, Update, Delete) operations
 * for role entities in the database. It also includes a custom method to find a role by its name.
 */
@Repository // Indicates that this interface is a Spring Data repository.
public interface RoleRepository extends JpaRepository<Role, Long> {

    /**
     * Finds a role by its name.
     *
     * @param name The name of the role to find.
     * @return An Optional containing the role if found, or an empty Optional if not found.
     */
    Optional<Role> findByName(String name);
}
