package com.example.cinematicketingsystem.controller;

import com.example.cinematicketingsystem.dto.SeatInfoDTO;
import com.example.cinematicketingsystem.dto.ShowtimeInfoDTO;
import com.example.cinematicketingsystem.entity.Movie;
import com.example.cinematicketingsystem.entity.Showtime;
import com.example.cinematicketingsystem.entity.ShowtimeSeat;
import com.example.cinematicketingsystem.entity.Ticket;
import com.example.cinematicketingsystem.service.movie.MovieService;
import com.example.cinematicketingsystem.service.showtime.ShowtimeService;
import com.example.cinematicketingsystem.service.showtimeSeat.ShowtimeSeatService;
import com.example.cinematicketingsystem.service.ticket.TicketService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * Controller class for public API endpoints.
 * <p>
 * This class handles public API requests related to movies, showtimes, seats, and tickets. It provides
 * endpoints for retrieving available movies, showtimes by movie ID, available seats for a showtime,
 * purchasing a seat, and refunding a ticket. The endpoints are prefixed with "/public".
 *
 * @see ShowtimeService
 * @see ShowtimeSeatService
 * @see MovieService
 * @see TicketService
 */
@AllArgsConstructor
@RestController
@RequestMapping("/public")
public class PublicController {
    private ShowtimeService showtimeService;
    private ShowtimeSeatService showtimeSeatService;
    private MovieService movieService;
    private TicketService ticketService;

    /**
     * Retrieves a list of available movies.
     *
     * @return a {@link ResponseEntity} containing a list of {@link Movie} objects
     */
    @GetMapping("/movies")
    public ResponseEntity<List<Movie>> getAvailableMovies() {
        List<Movie> movies = movieService.findAllAvailableMovies();
        return ResponseEntity.ok(movies);
    }

    /**
     * Retrieves a list of showtimes for a movie specified by its ID.
     *
     * @param movieId the ID of the movie
     * @return a {@link ResponseEntity} containing a list of {@link ShowtimeInfoDTO} objects
     */
    @GetMapping("/movies/{id}/showtimes")
    public ResponseEntity<List<ShowtimeInfoDTO>> getShowtimesByMovieId(@PathVariable("id") Long movieId) {
        List<ShowtimeInfoDTO> showtimes = showtimeService.findShowtimesByMovieId(movieId);
        return ResponseEntity.ok(showtimes);
    }

    /**
     * Retrieves a list of available seats for a showtime specified by its ID.
     *
     * @param showtimeId the ID of the showtime
     * @return a {@link ResponseEntity} containing a list of {@link SeatInfoDTO} objects
     */
    @GetMapping("/showtimes/{showtimeId}/seats")
    public ResponseEntity<List<SeatInfoDTO>> getAvailableSeats(@PathVariable("showtimeId") Long showtimeId) {
        List<SeatInfoDTO> availableSeats = showtimeSeatService.findAvailableSeats(showtimeId);
        return ResponseEntity.ok(availableSeats);
    }

    /**
     * Purchases a seat specified by its ID.
     *
     * @param seatId the ID of the seat to be purchased
     * @return a {@link ResponseEntity} containing the purchased {@link Ticket} object
     */
    @PostMapping("/seats/{id}")
    public ResponseEntity<Ticket> purchaseSeat(@PathVariable("id") Long seatId) {
        ShowtimeSeat showtimeSeat = showtimeSeatService.purchaseSeat(seatId);
        Showtime showtime = showtimeSeat.getShowtime();
        Ticket ticket = ticketService.generateTicket(showtimeSeat.getSeat(), showtime);
        return ResponseEntity.ok(ticket);
    }

    /**
     * Refunds a ticket specified by its ID.
     *
     * @param ticketId the ID of the ticket to be refunded
     * @return a {@link ResponseEntity} with a success message
     */
    @DeleteMapping("/ticket")
    public ResponseEntity<String> refundSeat(@RequestParam("id") String ticketId) {
        Ticket ticket = ticketService.findById(UUID.fromString(ticketId));
        Showtime showtime = showtimeService.findByTicket(ticket);
        showtimeSeatService.refundSeat(showtime, ticket);
        ticketService.deleteTicket(ticket);
        return ResponseEntity.ok("Ticket successfully refunded");
    }
}
