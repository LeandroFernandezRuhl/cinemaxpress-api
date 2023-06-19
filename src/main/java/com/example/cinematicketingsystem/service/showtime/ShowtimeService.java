package com.example.cinematicketingsystem.service.showtime;

import com.example.cinematicketingsystem.dto.ShowtimeInfoDTO;
import com.example.cinematicketingsystem.entity.Showtime;
import com.example.cinematicketingsystem.entity.Ticket;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Service interface for managing showtime entities.
 * <p>
 * This interface defines methods for creating, retrieving, and deleting showtime entities. It provides functionality
 * to manage showtimes based on their IDs, movies, and tickets. Implementations of this interface handle the business
 * logic associated with showtimes.
 *
 * @see Showtime
 */
public interface ShowtimeService {
    /**
     * Deletes a showtime with the specified ID.
     *
     * @param id the ID of the showtime to delete
     */
    void deleteShowtime(Long id);

    /**
     * Deletes all finished showtimes.
     */
    void deleteFinishedShowtimes();

    /**
     * Saves a new showtime with the specified start time, end time, movie ID, and room ID.
     *
     * @param startTime the start time of the showtime
     * @param endTime   the end time of the showtime
     * @param movieId   the ID of the movie associated with the showtime
     * @param roomId    the ID of the room where the showtime takes place
     */
    void saveShowtime(LocalDateTime startTime, LocalDateTime endTime, Long movieId, Long roomId);

    /**
     * Retrieves a list of showtime information DTOs (Data Transfer Objects) based on the specified movie ID.
     *
     * @param movieId the ID of the movie to retrieve showtimes for
     * @return a list of {@link ShowtimeInfoDTO} objects containing showtime information
     */
    List<ShowtimeInfoDTO> findShowtimesByMovieId(Long movieId);

    /**
     * Retrieves a showtime by its ID.
     *
     * @param id the ID of the showtime to retrieve
     * @return the Showtime object with the specified ID
     */
    Showtime findById(Long id);

    /**
     * Retrieves a showtime based on the specified ticket.
     *
     * @param ticket the ticket associated with the showtime
     * @return the Showtime object associated with the ticket
     */
    Showtime findByTicket(Ticket ticket);
}
