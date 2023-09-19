package com.example.cinematicketingsystem.dto;

import com.example.cinematicketingsystem.model.Showtime;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Data Transfer Object (DTO) class representing the creation of a {@link Showtime}.
 * <p>
 * This class encapsulates the information required to create a new showtime, including the room ID,
 * movie ID, start time, and end time of the showtime.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateShowtimeDTO {
    /**
     * The ID of the cinema room where the showtime will take place.
     */
    @NotNull
    private Long roomId;
    /**
     * The ID of the movie screened during the showtime.
     */
    @NotNull
    private Long movieId;
    /**
     * The start time of the showtime.
     */
    @NotNull
    private LocalDateTime startTime;
    /**
     * The end time of the showtime.
     */
    @NotNull
    private LocalDateTime endTime;
}