package com.example.cinematicketingsystem.controller;

import com.example.cinematicketingsystem.model.Movie;
import com.example.cinematicketingsystem.service.movie.MovieService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller class for managing movies.
 * <p>
 * This class handles HTTP requests related to movies and delegates the movie management operations
 * to the {@link MovieService}. It provides endpoints for retrieving all movies, searching movies by title,
 * saving a new movie, changing the availability of a movie, and deleting a movie. The endpoints are prefixed with
 * "/private/movies".
 */
@AllArgsConstructor
@RestController
@RequestMapping("/private/movies")
public class MovieController {
    private MovieService movieService;

    /**
     * Retrieves all movies.
     *
     * @return a {@link ResponseEntity} containing a list of {@link Movie} objects
     */
    @GetMapping()
    public ResponseEntity<List<Movie>> getAllMovies() {
        List<Movie> movies = movieService.findAllMovies();
        return ResponseEntity.ok(movies);
    }

    /**
     * Searches for movies based on the specified title.
     *
     * @param title the title of the movie to search for
     * @return a {@link ResponseEntity} containing the JSON representation of the movie list
     */
    @GetMapping("/search")
    public ResponseEntity<String> searchMovie(@RequestParam("title") String title) {
        String jsonMovieList = movieService.searchInMovieApi(title);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
        return new ResponseEntity<>(jsonMovieList, headers, HttpStatus.OK);
    }

    /**
     * Saves a new movie in the database.
     *
     * @param movie the {@link Movie} object to be saved
     * @return a {@link ResponseEntity} with a success message
     */
    @PostMapping()
    public ResponseEntity<String> saveMovie(@RequestBody @Valid Movie movie) {
        movieService.saveMovie(movie);
        return ResponseEntity.ok("Movie successfully saved in the database");
    }

    /**
     * Changes the availability of a movie.
     *
     * @param id the ID of the movie to update
     * @return a {@link ResponseEntity} containing the updated {@link Movie} object
     */
    @PatchMapping("/{id}")
    public ResponseEntity<Movie> changeAvailability(@PathVariable("id") Long id) {
        Movie updatedMovie = movieService.changeAvailability(id);
        return ResponseEntity.ok(updatedMovie);
    }

    /**
     * Deletes a movie from the database.
     *
     * @param id the ID of the movie to delete
     * @return a {@link ResponseEntity} with a success message
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMovie(@PathVariable("id") Long id) {
        movieService.deleteMovie(id);
        return ResponseEntity.ok("Movie successfully deleted");
    }
}
