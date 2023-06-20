package com.example.cinematicketingsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.example.cinematicketingsystem.entity.Showtime;

import java.time.LocalDateTime;

/**
 * Data Transfer Object (DTO) class representing information about a {@link Showtime}.
 * <p>
 * This class encapsulates information about a showtime, including the showtime ID,
 * the room ID where the showtime takes place, whether the room has 3D capability,
 * whether the room has surround sound capability, the movie ID being shown in the showtime,
 * the start time, and the end time of the showtime.
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShowtimeInfoDTO {
    /**
     * The ID of the showtime.
     */
    private Long showtimeId;
    /**
     * The ID of the cinema room where the showtime takes place.
     */
    private Long roomId;
    /**
     * Indicates whether the cinema room has 3D capability.
     */
    private Boolean roomHas3d;
    /**
     * Indicates whether the cinema room has surround sound capability.
     */
    private Boolean roomHasSurround;
    /**
     * The ID of the movie being shown in the showtime.
     */
    private Long movieId;
    /**
     * The start time of the showtime.
     */
    private LocalDateTime startTime;
    /**
     * The end time of the showtime.
     */
    private LocalDateTime endTime;
}
