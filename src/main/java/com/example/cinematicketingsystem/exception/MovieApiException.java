package com.example.cinematicketingsystem.exception;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class MovieApiException extends RuntimeException {
    private final boolean movieNotFound;

    public boolean isMovieNotFound() {
        return movieNotFound;
    }

    public MovieApiException(Exception e) {
        super(e);
        movieNotFound = false;
    }

    public MovieApiException(String title) {
        super(MovieApiException.buildMovieNotFoundMessage(title));
        this.movieNotFound = true;
    }

    private static String buildMovieNotFoundMessage(String title) {
        if (title.isBlank() || title.isEmpty()) {
            return "No movies found by the movie API";
        } else {
            return "No movies found with the title '" + title + "'";
        }
    }
}
