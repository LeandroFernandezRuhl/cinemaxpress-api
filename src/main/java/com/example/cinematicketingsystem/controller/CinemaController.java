package com.example.cinematicketingsystem.controller;

import com.example.cinematicketingsystem.dto.CreateShowtimeDTO;
import com.example.cinematicketingsystem.dto.SeatDTO;
import com.example.cinematicketingsystem.entity.*;
import com.example.cinematicketingsystem.service.cinemaRoom.CinemaRoomService;
import com.example.cinematicketingsystem.service.movie.MovieService;
import com.example.cinematicketingsystem.service.showtime.ShowtimeService;
import com.example.cinematicketingsystem.service.showtimeSeat.ShowtimeSeatService;
import com.example.cinematicketingsystem.service.ticket.TicketService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/cinema")
public class CinemaController {
    private final CinemaRoomService cinemaRoomService;
    private final ShowtimeService showtimeService;
    private final ShowtimeSeatService showtimeSeatService;
    private final TicketService ticketService;
    private final MovieService movieService;

    @PostMapping("/saveShowtime")
    public ResponseEntity<String> saveShowtime(@RequestBody @Valid CreateShowtimeDTO request) {
        Movie movie = movieService.findById(request.getMovieId());
        CinemaRoom room = cinemaRoomService.findById(request.getRoomId());
        showtimeService.saveShowtime(movie, room, request.getStartTime(), request.getEndTime());
        return ResponseEntity.ok("Showtime sucessfully saved");
    }

    @GetMapping("/availableSeats")
    public ResponseEntity<List<SeatDTO>> getAvailableSeats(@RequestParam("showtimeId") Long showtimeId) {

        // Retrieve the available seats for the showtime
        List<SeatDTO> availableSeats = showtimeSeatService.findAvailableSeats(showtimeId);

        return ResponseEntity.ok(availableSeats);
    }

    @PostMapping("/purchaseSeat")
    public ResponseEntity<Ticket> purchaseSeat(@RequestParam("seatId") Long seatId) {
        ShowtimeSeat showtimeSeat = showtimeSeatService.purchaseSeat(seatId);
        Showtime showtime = showtimeSeat.getShowtime();

        Ticket ticket = ticketService.generateTicket(showtimeSeat.getSeat(), showtime);

        return ResponseEntity.ok(ticket);
    }

    @PostMapping("/refundSeat")
    public ResponseEntity<String> refundSeat(@RequestParam("ticketId") String ticketId) {
        Ticket ticket = ticketService.findById(UUID.fromString(ticketId));
        Showtime showtime = showtimeService.findByTicket(ticket);
        showtimeSeatService.refundSeat(showtime, ticket);
        ticketService.deleteTicket(ticket);
        return ResponseEntity.ok("Ticket successfully refunded");
    }

    @GetMapping("/searchMovie")
    public ResponseEntity<String> searchMovie(@RequestParam("title") String title) {
        String jsonMovieList = movieService.searchInMovieApi(title);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
        return new ResponseEntity<>(jsonMovieList, headers, HttpStatus.OK);
    }

    @PostMapping("/saveMovie")
    public ResponseEntity<String> saveMovie(@RequestBody @Valid Movie movie) {
        movieService.saveMovie(movie);
        return ResponseEntity.ok("Movie successfully saved in the database");
    }

    @DeleteMapping("/deleteMovie")
    public ResponseEntity<String> deleteMovie(@RequestParam("id") Long id) {
        movieService.deleteMovie(id);
        return ResponseEntity.ok("Movie successfully deleted");
    }

    @GetMapping("/movies")
    public ResponseEntity<List<Movie>> getMovies() {
        List<Movie> movies = movieService.findAllMovies();
        return ResponseEntity.ok(movies);
    }
}
