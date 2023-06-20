package com.example.cinematicketingsystem.exception;

import java.io.IOException;
import org.json.JSONException;

/**
 * Exception thrown when there is an error in the movie API integration.
 * <p>
 * This exception is thrown when there is an issue with the movie API integration, such as a failure to retrieve movie data
 * or an error in processing movie-related requests. It is a runtime exception, indicating an unexpected error in the movie API.
 */
public class MovieApiException extends RuntimeException {
    /**
     * True if the exception is caused by a movie not found, false otherwise.
     */
    private Boolean causedByMovieNotFound;

    /**
     * Constructs a new {@code MovieApiException} with the specified underlying exception.
     * Used when there is an {@link IOException} or {@link InterruptedException} while using
     * the external movie API. Also used with {@link JSONException} thrown while serializing/deserializing movies.
     *
     * @param e the underlying exception that caused the movie API error
     */
    public MovieApiException(Exception e) {
        super(e);
        causedByMovieNotFound = false;
    }

    /**
     * Constructs a new {@code MovieApiException} with the specified movie title.
     * Used when the exception is caused by the external API not finding any movies.
     *
     * @param title the title of the movie that was not found in the movie API
     */
    public MovieApiException(String title) {
        super(MovieApiException.buildMovieNotFoundMessage(title));
        this.causedByMovieNotFound = true;
    }

    /**
     * Builds the error message for a movie not found scenario based on the provided title.
     *
     * @param title the title of the movie that was not found
     * @return the error message indicating the movie was not found
     */
    private static String buildMovieNotFoundMessage(String title) {
        if (title.isBlank() || title.isEmpty()) {
            return "No movies found by the movie API";
        } else {
            return "No movies found with the title '" + title + "'";
        }
    }

    /**
     * Getter for the 'causedByMovieNotFound' field.
     *
     * @return {@code true} if the exception is caused by a movie not found, {@code false} otherwise
     */
    public Boolean isCausedByMovieNotFound() {
        return causedByMovieNotFound;
    }
}
