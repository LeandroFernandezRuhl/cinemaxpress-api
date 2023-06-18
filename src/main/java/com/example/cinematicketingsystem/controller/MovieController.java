package com.example.cinematicketingsystem.controller;

import com.example.cinematicketingsystem.entity.Movie;
import com.example.cinematicketingsystem.service.movie.MovieService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/private/movies")
public class MovieController {
    private MovieService movieService;

    @GetMapping()
    public ResponseEntity<List<Movie>> getAllMovies() {
        List<Movie> movies = movieService.findAllMovies();
        return ResponseEntity.ok(movies);
    }
    @GetMapping("/search")
    public ResponseEntity<String> searchMovie(@RequestParam("title") String title) {
        String jsonMovieList = movieService.searchInMovieApi(title);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
        return new ResponseEntity<>(jsonMovieList, headers, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<String> saveMovie(@RequestBody @Valid Movie movie) {
        movieService.saveMovie(movie);
        return ResponseEntity.ok("Movie successfully saved in the database");
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Movie> changeAvailability(@PathVariable("id") Long id) {
        Movie updatedMovie = movieService.changeAvailability(id);
        return ResponseEntity.ok(updatedMovie);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMovie(@PathVariable("id") Long id) {
        movieService.deleteMovie(id);
        return ResponseEntity.ok("Movie successfully deleted");
    }

}
