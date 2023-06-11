package com.example.cinematicketingsystem.apierror;

import com.example.cinematicketingsystem.entity.Showtime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
public class OverlappingError extends ApiSubError {
    private String message;
    private Long id;
    private LocalDateTime start;
    private LocalDateTime end;

    public OverlappingError(Showtime showtime) {
        id = showtime.getId();
        message = "Showtime with ID " + id + " overlaps with the provided time range";
        start = showtime.getStartTime();
        end = showtime.getEndTime();
    }
}
