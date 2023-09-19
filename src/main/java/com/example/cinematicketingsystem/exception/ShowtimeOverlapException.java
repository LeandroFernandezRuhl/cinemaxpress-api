package com.example.cinematicketingsystem.exception;

import com.example.cinematicketingsystem.apierror.suberror.OverlappingError;
import com.example.cinematicketingsystem.model.Showtime;

import java.util.ArrayList;
import java.util.List;

/**
 * Exception thrown when there is an overlap between showtimes.
 * <p>
 * This exception is thrown when an attempt to create or modify a showtime results in an overlap with existing showtimes.
 * It is a runtime exception, indicating that the showtime overlap violates the business rules of the system.
 *
 * @see Showtime
 */
public class ShowtimeOverlapException extends RuntimeException {
    /**
     * Contains a list of {@link OverlappingError} objects, each representing a specific
     * overlapping showtime and providing details about the overlap.
     */
    private final List<OverlappingError> overlappingErrors;

    /**
     * Constructs a new {@code ShowtimeOverlapException} with the specified error message and overlapping showtimes.
     *
     * @param message              the error message describing the showtime overlap
     * @param overlappingShowtimes the list of showtimes that overlap with the new or modified showtime
     */
    public ShowtimeOverlapException(String message, List<Showtime> overlappingShowtimes) {
        super(message);
        overlappingErrors = new ArrayList<>();
        initializeOverlappingErrors(overlappingShowtimes);
    }

    /**
     * Initializes the list of overlapping errors based on the provided overlapping showtimes.
     *
     * @param overlappingShowtimes the list of showtimes that overlap with the new or modified showtime
     */
    private void initializeOverlappingErrors(List<Showtime> overlappingShowtimes) {
        for (Showtime showtime : overlappingShowtimes) {
            overlappingErrors.add(new OverlappingError(showtime));
        }
    }

    /**
     * Retrieves the list of overlapping errors.
     *
     * @return the list of overlapping errors
     */
    public List<OverlappingError> getOverlappingErrors() {
        return overlappingErrors;
    }
}
