package com.example.cinematicketingsystem.service.movie;

import com.example.cinematicketingsystem.entity.*;
import com.example.cinematicketingsystem.exception.EntityAlreadyExistsException;
import com.example.cinematicketingsystem.exception.EntityNotFoundException;
import com.example.cinematicketingsystem.exception.MovieApiException;
import com.example.cinematicketingsystem.movieapi.MovieJSONConverter;
import com.example.cinematicketingsystem.movieapi.MovieApiClient;
import com.example.cinematicketingsystem.repository.MovieRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Service implementation class for managing movie entities.
 * <p>
 * This class implements the {@link MovieService} interface and provides the implementation for all the methods
 * defined in the interface. It uses the {@link MovieRepository} to interact with the database and perform CRUD
 * operations on movie entities. It also uses the {@link MovieApiClient} and {@link MovieJSONConverter} for external
 * movie API integration and JSON conversion, respectively.
 *
 * @see Movie
 */
@Slf4j
@AllArgsConstructor
@Service
public class MovieServiceImpl implements MovieService {
    private MovieRepository movieRepository;
    private MovieApiClient movieApiClient;
    private MovieJSONConverter movieJsonConverter;

    /**
     * {@inheritDoc}
     *
     * @param movie the movie to save
     * @throws EntityAlreadyExistsException if a movie with the same ID already exists in the database
     */
    public void saveMovie(Movie movie) {
        log.debug("Creating movie with ID={}", movie.getId());
        if (movieRepository.existsById(movie.getId())) {
            throw new EntityAlreadyExistsException(Movie.class, movie.getId().toString());
        }
        movieRepository.save(movie);
    }

    /**
     * {@inheritDoc}
     *
     * @param id the ID of the movie to change availability
     * @return the updated movie with the new availability status
     * @throws EntityNotFoundException if the movie with the specified ID is not found
     */
    public Movie changeAvailability(Long id) {
        log.debug("Changing availability of movie with ID={}", id);
        Movie movie = findById(id);
        movie.setAvailable(!movie.getAvailable());
        return movieRepository.save(movie);
    }

    /**
     * {@inheritDoc}
     *
     * @param id the ID of the movie to delete
     * @throws EntityNotFoundException         if the movie with the specified ID is not found
     * @throws DataIntegrityViolationException if the deletion violates data integrity constraints
     */
    public void deleteMovie(Long id) {
        log.debug("Deleting movie with ID={}", id);
        Movie movie = findById(id);
        movieRepository.delete(movie);
    }

    /**
     * {@inheritDoc}
     *
     * @param id the ID of the movie
     * @return the movie with the specified ID, or null if not found
     * @throws EntityNotFoundException if the movie with the specified ID is not found
     */
    public Movie findById(Long id) {
        log.debug("Finding movie with ID={}", id);
        return movieRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Movie.class, "id", id.toString()));
    }

    /**
     * {@inheritDoc}
     *
     * @return a list of all movies
     * @throws jakarta.persistence.EntityNotFoundException if the movie with the specified ID is not found
     */
    public List<Movie> findAllMovies() {
        log.debug("Finding all movies");
        List<Movie> movies = new ArrayList<>();
        movieRepository.findAll().iterator().forEachRemaining(movies::add);
        if (movies.size() == 0)
            throw new jakarta.persistence.EntityNotFoundException("No movies found");
        return movies;
    }

    /**
     * {@inheritDoc}
     *
     * @return a list of available movies
     * @throws jakarta.persistence.EntityNotFoundException if the movie with the specified ID is not found
     */
    public List<Movie> findAllAvailableMovies() {
        log.debug("Finding all available movies");
        List<Movie> movies = movieRepository.findAllByAvailableIsTrue();
        if (movies.size() == 0)
            throw new jakarta.persistence.EntityNotFoundException("No available movies found");
        return movies;
    }

    /**
     * {@inheritDoc}
     *
     * @param title the title to search for in the movie API
     * @return a string representing the search result or response from the movie API
     * @throws MovieApiException if an error occurs during the API search or if no movies are found in the API response
     */
    public String searchInMovieApi(String title) {
        log.debug("Searching for a movie with title '" + title + "'");
        String jsonResponse = null;
        try {
            jsonResponse = movieApiClient.search(title);
        } catch (IOException | InterruptedException e) {
            throw new MovieApiException(e);
        }
        List<Movie> movieList = movieJsonConverter.JSONToMovieList(jsonResponse);
        if (movieList.isEmpty()) {
            throw new MovieApiException(title);
        }
        return movieJsonConverter.MovieListToJSON(movieList);
    }
}
