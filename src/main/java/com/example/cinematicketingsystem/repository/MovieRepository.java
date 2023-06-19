package com.example.cinematicketingsystem.repository;

import com.example.cinematicketingsystem.entity.Movie;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for managing movie entities in the database.
 * <p>
 * This interface extends the Spring Data `CrudRepository` interface, which provides generic CRUD (Create, Read,
 * Update, Delete) operations for the `Movie` entity.
 *
 * @see Movie
 * @see org.springframework.data.repository.CrudRepository
 */
@Repository
public interface MovieRepository extends CrudRepository<Movie, Long> {
    /**
     * Retrieves all movies that are marked as available.
     *
     * @return a list of available movies
     */
    List<Movie> findAllByAvailableIsTrue();
}
