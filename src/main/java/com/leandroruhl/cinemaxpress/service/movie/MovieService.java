package com.leandroruhl.cinemaxpress.service.movie;

import com.leandroruhl.cinemaxpress.model.Movie;

import java.util.List;

/**
 * Service interface for managing movie entities.
 * <p>
 * The `MovieService` interface defines methods for saving, deleting, retrieving, and searching movie information.
 * It provides operations to save a movie, delete a movie by ID, retrieve all movies, retrieve available movies,
 * find a movie by ID, change the availability of a movie, and search for movies in an external movie API.
 *
 * @see Movie
 */
public interface MovieService {
    /**
     * Saves a movie.
     *
     * @param movie the movie to save
     */
    void saveMovie(Movie movie);

    /**
     * Deletes a movie by its ID.
     *
     * @param id the ID of the movie to delete
     */
    void deleteMovie(Long id);

    /**
     * Retrieves all movies.
     *
     * @return a list of all movies
     */
    List<Movie> findAllMovies();

    /**
     * Retrieves all available movies.
     *
     * @return a list of available movies
     */
    List<Movie> findAllAvailableMovies();

    /**
     * Retrieves a movie by its ID.
     *
     * @param id the ID of the movie
     * @return the movie with the specified ID
     */
    Movie findById(Long id);

    /**
     * Changes the availability of a movie.
     *
     * @param id the ID of the movie to change availability
     * @return the updated movie with the new availability status
     */
    Movie changeAvailability(Long id);

    /**
     * Searches for movies in an external movie API based on the given title.
     *
     * @param title the title to search for in the movie API
     * @return a string representing the search result or response from the movie API
     */
    String searchInMovieApi(String title);
}
