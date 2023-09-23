package com.example.cinematicketingsystem.controller;

import com.example.cinematicketingsystem.dto.SeatInfoDTO;
import com.example.cinematicketingsystem.dto.ShowtimeInfoDTO;
import com.example.cinematicketingsystem.model.Movie;
import com.example.cinematicketingsystem.model.Showtime;
import com.example.cinematicketingsystem.model.ShowtimeSeat;
import com.example.cinematicketingsystem.model.Ticket;
import com.example.cinematicketingsystem.service.movie.MovieService;
import com.example.cinematicketingsystem.service.showtime.ShowtimeService;
import com.example.cinematicketingsystem.service.showtimeSeat.ShowtimeSeatService;
import com.example.cinematicketingsystem.service.ticket.TicketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * Controller class for public API endpoints that are available to all users.
 * <p>
 * This class handles public API requests related to the booking of seats for a movie. It provides
 * endpoints for retrieving available movies, showtimes by movie ID, available seats for a showtime,
 * purchasing a seat, and refunding a ticket.
 *
 * @see ShowtimeService
 * @see ShowtimeSeatService
 * @see MovieService
 * @see TicketService
 */
@AllArgsConstructor
@Tag(name = "Seat Booking", description = "Endpoints for regular users that want to book seats for a showtime")
//@CrossOrigin(origins = "http://localhost:3000/", maxAge = 3600)
@RestController
@RequestMapping("/seat-booking")
public class SeatBookingController {
    private ShowtimeService showtimeService;
    private ShowtimeSeatService showtimeSeatService;
    private MovieService movieService;
    private TicketService ticketService;

    /**
     * Retrieves a list of available movies.
     *
     * @return a {@link ResponseEntity} containing a list of {@link Movie} objects
     */
    @Operation(
            summary = "Get a list of available movies",
            description = "Retrieves a list of the available movies"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of available movies")})
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
    @Operation(
            summary = "Get the scheduled showtimes for a movie",
            description = "Retrieves a list of showtimes for a movie, specified by its ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of showtimes for the movie"),
            @ApiResponse(responseCode = "404", description = "Movie not found")})
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
    @Operation(
            summary = "Retrieve the available seats for a showtime",
            description = "Retrieves a list of available seats for a showtime, specified by its ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of available seats for the showtime"),
            @ApiResponse(responseCode = "404", description = "Showtime not found"),
    })
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
    @Operation(
            summary = "Purchase a seat for a showtime",
            description = "Purchases a seat specified by its ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Seat successfully purchased"),
            @ApiResponse(responseCode = "404", description = "Seat not found"),
    })
    @PostMapping("/seats/{id}")
    public ResponseEntity<Ticket> purchaseSeat(@PathVariable("id") Long seatId) {
        ShowtimeSeat showtimeSeat = showtimeSeatService.purchaseSeat(seatId);
        Showtime showtime = showtimeSeat.getShowtime();
        Ticket ticket = ticketService.generateTicket(showtimeSeat.getSeat(), showtime);
        return ResponseEntity.ok(ticket);
    }

    /**
     * Refunds a seat specified by its ticket ID.
     *
     * @param ticketId the ID of the seat's ticket
     * @return a {@link ResponseEntity} with a success message
     */
    @Operation(
            summary = "Cancel a reserved seat and request a refund",
            description = "Refunds a seat specified by its ticket ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Seat successfully refunded"),
            @ApiResponse(responseCode = "404", description = "Ticket not found"),
    })
    @DeleteMapping("/tickets")
    public ResponseEntity<String> refundSeat(@RequestParam("ticketId") String ticketId) {
        Ticket ticket = ticketService.findById(UUID.fromString(ticketId));
        Showtime showtime = showtimeService.findByTicket(ticket);
        showtimeSeatService.refundSeat(showtime, ticket);
        ticketService.deleteTicket(ticket);
        return ResponseEntity.ok("Ticket successfully refunded");
    }
}
