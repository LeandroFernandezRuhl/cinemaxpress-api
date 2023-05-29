package com.example.cinematicketingsystem.controller;

import com.example.cinematicketingsystem.entity.Showtime;
import com.example.cinematicketingsystem.service.showtime.ShowtimeService;
import com.example.cinematicketingsystem.service.showtimeSeat.ShowtimeSeatsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/cinema")
public class CinemaController {
    private final ShowtimeService showtimeService;
    private final ShowtimeSeatsService showtimeSeatsService;
/*
    @GetMapping("/{movieId}/showtimes/{showtimeId}/availableSeats")
    public ResponseEntity<AvailableSeatsDTO> getAvailableSeats(@PathVariable Long movieId, @PathVariable Long showtimeId) {
        // Check if the movie exists
        if (!movieService.existsById(movieId)) {
            throw new NotFoundException("Movie not found");
        }

        // Retrieve the showtime by ID
        Showtime showtime = showtimeService.findById(showtimeId);

        // Check if the showtime exists
        if (showtime == null) {
            throw new NotFoundException("Showtime not found");
        }

        // Retrieve the available seats for the showtime
        return showtimeSeatsService.getAvailableSeatsByShowtimeId(showtimeId);
    } */
}
