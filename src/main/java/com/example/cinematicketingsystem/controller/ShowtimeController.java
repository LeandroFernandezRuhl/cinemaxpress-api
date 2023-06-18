package com.example.cinematicketingsystem.controller;

import com.example.cinematicketingsystem.dto.CreateShowtimeDTO;
import com.example.cinematicketingsystem.dto.SeatInfoDTO;
import com.example.cinematicketingsystem.dto.ShowtimeInfoDTO;
import com.example.cinematicketingsystem.entity.CinemaRoom;
import com.example.cinematicketingsystem.entity.Movie;
import com.example.cinematicketingsystem.entity.Showtime;
import com.example.cinematicketingsystem.service.cinemaRoom.CinemaRoomService;
import com.example.cinematicketingsystem.service.movie.MovieService;
import com.example.cinematicketingsystem.service.showtime.ShowtimeService;
import com.example.cinematicketingsystem.service.showtimeSeat.ShowtimeSeatService;
import com.example.cinematicketingsystem.service.ticket.TicketService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/private/showtimes")
public class ShowtimeController {
    private ShowtimeService showtimeService;
    private CinemaRoomService cinemaRoomService;
    private TicketService ticketService;
    private MovieService movieService;

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteShowtime(@PathVariable("id") Long id) {
        Showtime showtime = showtimeService.findById(id);
        //ver que hacer con esto
        if (LocalDateTime.now().isBefore(showtime.getStartTime())) {
            ticketService.deleteTicketsByShowtime(showtime);
        }
        showtimeService.deleteShowtime(id);
        return ResponseEntity.ok("Showtime successfully deleted");
    }

    @DeleteMapping()
    public ResponseEntity<String> deleteFinishedShowtimes() {
        showtimeService.deleteFinishedShowtimes();
        return ResponseEntity.ok("Finished showtimes successfully deleted");
    }

    @PostMapping()
    public ResponseEntity<String> saveShowtime(@RequestBody @Valid CreateShowtimeDTO request) {
        //mover estos dos adentro de save showtime
        Movie movie = movieService.findById(request.getMovieId());
        CinemaRoom room = cinemaRoomService.findById(request.getRoomId());
        showtimeService.saveShowtime(movie, room, request.getStartTime(), request.getEndTime());
        return ResponseEntity.ok("Showtime successfully saved");
    }

}
