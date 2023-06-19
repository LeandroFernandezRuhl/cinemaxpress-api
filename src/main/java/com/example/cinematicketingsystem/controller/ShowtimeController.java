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
    private TicketService ticketService;

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteShowtime(@PathVariable("id") Long id) {
        Showtime showtime = showtimeService.findById(id);
        // if the showtime hasn't started yet refund the tickets
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
        showtimeService.saveShowtime(request.getStartTime(), request.getEndTime(), request.getMovieId(),
                request.getRoomId());
        return ResponseEntity.ok("Showtime successfully saved");
    }

}
