package com.example.cinematicketingsystem.service.movie;

import com.example.cinematicketingsystem.entity.Movie;

import java.util.List;

public interface MovieService {
    void saveMovie(Movie movie);
    void deleteMovie(Long id);
    List<Movie> findAllMovies();
    Movie findById(Long id);
    String searchInMovieApi(String title);
}
