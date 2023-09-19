package com.example.cinematicketingsystem.repository;

import com.example.cinematicketingsystem.model.Ticket;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository interface for managing ticket entities in the database.
 * <p>
 * This interface extends the Spring Data `CrudRepository` interface, which provides generic CRUD (Create, Read,
 * Update, Delete) operations for the `Ticket` entity. In addition to the default CRUD methods, this repository
 * interface defines several custom query methods for retrieving and deleting tickets based on specific criteria.
 *
 * @see Ticket
 * @see org.springframework.data.repository.CrudRepository
 */
@Repository
public interface TicketRepository extends CrudRepository<Ticket, String> {
    /**
     * Deletes all tickets with the specified showtime start time.
     *
     * @param startTime the start time of the showtime
     * @return a list of deleted tickets
     */
    List<Ticket> deleteAllByShowtimeStartTime(LocalDateTime startTime);

    /**
     * Retrieves tickets issued within the specified date range.
     *
     * @param from the start date/time of the range
     * @param to   the end date/time of the range
     * @return a list of tickets issued within the date range
     */
    List<Ticket> findByIssueDateTimeIsBetween(LocalDateTime from, LocalDateTime to);

    /**
     * Retrieves tickets associated with a specific movie title.
     *
     * @param title the title of the movie
     * @return a list of tickets associated with the movie
     */
    List<Ticket> findByMovieTitle(String title);

    /**
     * Retrieves a ticket with the specified UUID.
     *
     * @param uuid the UUID of the ticket
     * @return an optional containing the ticket, or empty if not found
     */
    Optional<Ticket> findById(UUID uuid);
}
