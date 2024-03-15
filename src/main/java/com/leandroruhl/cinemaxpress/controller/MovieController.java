package com.leandroruhl.cinemaxpress.controller;

import com.leandroruhl.cinemaxpress.model.Movie;
import com.leandroruhl.cinemaxpress.service.movie.MovieService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
 * saving a new movie, changing the availability of a movie, and deleting a movie.
 */
@Tag(name = "Movies", description = "Endpoints for searching, creating and deleting movies")
@AllArgsConstructor
@RestController
@RequestMapping("/cinema-management/movies")
public class MovieController {
    private MovieService movieService;

    /**
     * Retrieves all movies.
     *
     * @return a {@link ResponseEntity} containing a list of {@link Movie} objects
     */
    @Operation(
            summary = "Retrieve all movies in the database",
            description = "Get a list of all movies"
    )
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
    @Operation(
            summary = "Search movies in external API",
            description = "Search by movie title in TMBD (The Movie Database)"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Search results")
    })
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
    @Operation(
            summary = "Create a new movie",
            description = "Save a new movie in the database."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Movie saved successfully")
    })
    @PostMapping()
    public ResponseEntity<String> saveMovie(@RequestBody Movie movie) {
        movieService.saveMovie(movie);
        return ResponseEntity.ok("Movie successfully saved in the database");
    }

    /**
     * Changes the availability of a movie.
     *
     * @param id the ID of the movie to update
     * @return a {@link ResponseEntity} containing the updated {@link Movie} object
     */
    @Operation(
            summary = "Change the availability of a movie",
            description = "Make a movie available or unavailable for the public"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Availability changed successfully")
    })
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
    @Operation(
            summary = "Delete a movie",
            description = "Delete a movie from the database by its ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Movie deleted successfully")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMovie(@PathVariable("id") Long id) {
        movieService.deleteMovie(id);
        return ResponseEntity.ok("Movie successfully deleted");
    }
}
