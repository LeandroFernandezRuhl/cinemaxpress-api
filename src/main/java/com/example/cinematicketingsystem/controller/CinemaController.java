package com.example.cinematicketingsystem.controller;

import com.example.cinematicketingsystem.dto.SeatDTO;
import com.example.cinematicketingsystem.entity.Showtime;
import com.example.cinematicketingsystem.entity.ShowtimeSeat;
import com.example.cinematicketingsystem.service.showtime.ShowtimeService;
import com.example.cinematicketingsystem.service.showtimeSeat.ShowtimeSeatService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.mapper.Mapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/cinema")
public class CinemaController {
    private final ShowtimeService showtimeService;
    private final ShowtimeSeatService showtimeSeatService;

    @GetMapping("/availableSeats")
    public ResponseEntity<List<SeatDTO>> getAvailableSeats(
            @RequestParam("showtimeId") Long showtimeId) {

        // Retrieve the available seats for the showtime
        List<SeatDTO> list = showtimeSeatService.findAvailableSeats(showtimeId);

        return ResponseEntity.ok(list);
    }
}
