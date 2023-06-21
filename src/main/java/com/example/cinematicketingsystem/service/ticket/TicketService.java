package com.example.cinematicketingsystem.service.ticket;

import com.example.cinematicketingsystem.dto.TicketInfoDTO;
import com.example.cinematicketingsystem.entity.Seat;
import com.example.cinematicketingsystem.entity.Showtime;
import com.example.cinematicketingsystem.entity.Ticket;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Service interface for managing tickets.
 * <p>
 * This interface defines methods for generating tickets, deleting tickets, retrieving tickets by ID, deleting tickets
 * associated with a showtime, and retrieving ticket statistics based on time range and movie title.
 *
 * @see Ticket
 */
public interface TicketService {
    /**
     * Generates a ticket for the specified seat and showtime.
     *
     * @param seat     the seat for which to generate the ticket
     * @param showtime the showtime for which to generate the ticket
     * @return the generated Ticket object
     */
    Ticket generateTicket(Seat seat, Showtime showtime);

    /**
     * Deletes the specified ticket.
     *
     * @param ticket the ticket to delete
     */
    void deleteTicket(Ticket ticket);

    /**
     * Retrieves a ticket by its UUID.
     *
     * @param id the UUID of the ticket to retrieve
     * @return the Ticket object with the specified UUID
     */
    Ticket findById(UUID id);

    /**
     * Deletes all tickets associated with the specified showtime.
     *
     * @param showtime the showtime for which to delete tickets
     */
    void deleteTicketsByShowtime(Showtime showtime);

    /**
     * Retrieves ticket statistics based on the specified time range.
     *
     * @param fromDateTime the starting date and time of the time range
     * @param toDateTime   the ending date and time of the time range
     * @return a {@link TicketInfoDTO} object containing the ticket statistics for the specified time range
     */
    TicketInfoDTO getTicketStatsByTimeRange(LocalDate fromDateTime, LocalDate toDateTime);

    /**
     * Retrieves ticket statistics based on the specified movie title.
     *
     * @param title the title of the movie for which to retrieve ticket statistics
     * @return a {@link TicketInfoDTO} object containing the ticket statistics for the specified movie ID
     */
    TicketInfoDTO getTicketStatsByMovieTitle(String title);
}
