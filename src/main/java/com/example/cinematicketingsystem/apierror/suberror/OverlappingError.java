package com.example.cinematicketingsystem.apierror.suberror;

import com.example.cinematicketingsystem.model.Showtime;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * Represents an error indicating overlapping showtimes.
 * <p>
 * The {@code OverlappingError} class extends {@link SubError} and is used to represent an error that occurs when a showtime
 * overlaps with the provided time range. It contains information about the overlapping showtime, including its ID, start time,
 * end time, and a descriptive message.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class OverlappingError extends SubError {
    private String message;
    private Long id;
    private LocalDateTime start;
    private LocalDateTime end;

    /**
     * Constructs an instance of the {@code OverlappingError} class based on the provided showtime.
     *
     * @param showtime the showtime that overlaps with the provided time range
     */
    public OverlappingError(Showtime showtime) {
        id = showtime.getId();
        message = "Showtime with id=" + id + " overlaps with the provided time range";
        start = showtime.getStartTime();
        end = showtime.getEndTime();
    }
}

