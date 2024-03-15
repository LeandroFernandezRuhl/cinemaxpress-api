package com.leandroruhl.cinemaxpress.repository;

import com.leandroruhl.cinemaxpress.model.Showtime;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing showtime entities in the database.
 * <p>
 * This interface extends the Spring Data `CrudRepository` interface, which provides generic CRUD (Create, Read,
 * Update, Delete) operations for the `Showtime` entity. In addition to the default CRUD methods, this repository
 * interface defines several custom query methods for retrieving showtimes based on specific criteria.
 *
 * @see Showtime
 * @see org.springframework.data.repository.CrudRepository
 */
@Repository
public interface ShowtimeRepository extends CrudRepository<Showtime, Long> {
    /**
     * Retrieves all showtimes associated with a specific movie ID that have an end time after the current time,
     * which means they haven't ended yet.
     *
     * @param movieId the ID of the movie
     * @param now     the current date and time
     * @return a list of showtimes that meet the criteria
     */
    List<Showtime> findAllByMovieIdAndEndTimeAfter(Long movieId, LocalDateTime now);

    /**
     * Retrieves a showtime with the specified start time and cinema room ID.
     *
     * @param dateTime the start time of the showtime
     * @param id       the ID of the cinema room
     * @return an optional containing the matching showtime, or empty if not found
     */
    Optional<Showtime> findByStartTimeAndCinemaRoom_Id(LocalDateTime dateTime, Long id);

    /**
     * Retrieves all showtimes that have an end time before the current time, which means they have already ended.
     *
     * @param now the current date and time
     * @return a list of showtimes that meet the criteria
     */
    List<Showtime> findAllByEndTimeBefore(LocalDateTime now);

    /**
     * Finds showtimes that overlap with the specified time range for a given cinema room ID.
     *
     * @param roomId       the ID of the cinema room
     * @param newStartTime the start time of the new showtime
     * @param newEndTime   the end time of the new showtime
     * @return a list of showtimes that overlap with the specified time range
     */
    @Query(value = "SELECT * FROM showtime " +
            "WHERE cinema_room_id = :roomId " +
            "AND (start_time, end_time) OVERLAPS (:newStartTime, :newEndTime)",
            nativeQuery = true)
    List<Showtime> findOverlappingShowtimes(
            @Param("roomId") Long roomId,
            @Param("newStartTime") LocalDateTime newStartTime,
            @Param("newEndTime") LocalDateTime newEndTime);
}
