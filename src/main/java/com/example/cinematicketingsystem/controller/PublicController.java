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

@AllArgsConstructor
@RestController
@RequestMapping("/public")
public class PublicController {
    private ShowtimeService showtimeService;
    private ShowtimeSeatService showtimeSeatService;
    private MovieService movieService;
    private TicketService ticketService;

    @GetMapping("/movies")
    public ResponseEntity<List<Movie>> getAvailableMovies() {
        List<Movie> movies = movieService.findAllAvailableMovies();
        return ResponseEntity.ok(movies);
    }

    @GetMapping("/movies/{id}/showtimes")
    public ResponseEntity<List<ShowtimeInfoDTO>> getShowtimesByMovieId(@PathVariable("id") Long movieId) {
        List<ShowtimeInfoDTO> showtimes = showtimeService.findShowtimesByMovieId(movieId);
        return ResponseEntity.ok(showtimes);
    }

    @GetMapping("/showtimes/{showtimeId}/seats")
    public ResponseEntity<List<SeatInfoDTO>> getAvailableSeats(@PathVariable("showtimeId") Long showtimeId) {
        List<SeatInfoDTO> availableSeats = showtimeSeatService.findAvailableSeats(showtimeId);
        return ResponseEntity.ok(availableSeats);
    }

    @PostMapping("/seats/{id}")
    public ResponseEntity<Ticket> purchaseSeat(@PathVariable("id") Long seatId) {
        ShowtimeSeat showtimeSeat = showtimeSeatService.purchaseSeat(seatId);
        Showtime showtime = showtimeSeat.getShowtime();
        Ticket ticket = ticketService.generateTicket(showtimeSeat.getSeat(), showtime);
        return ResponseEntity.ok(ticket);
    }

    @DeleteMapping("/ticket")
    public ResponseEntity<String> refundSeat(@RequestParam("id") String ticketId) {
        Ticket ticket = ticketService.findById(UUID.fromString(ticketId));
        Showtime showtime = showtimeService.findByTicket(ticket);
        showtimeSeatService.refundSeat(showtime, ticket);
        ticketService.deleteTicket(ticket);
        return ResponseEntity.ok("Ticket successfully refunded");
    }
}
