package com.example.cinematicketingsystem.service.movie;

import com.example.cinematicketingsystem.entity.Movie;

import java.util.List;

public interface MovieService {
    void saveMovie(Movie movie);
    List<Movie> findAllMovies();
    String searchInMovieApi(String title);
}
