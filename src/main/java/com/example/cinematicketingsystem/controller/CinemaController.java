package com.example.cinematicketingsystem.controller;

import com.example.cinematicketingsystem.dto.SeatDTO;
import com.example.cinematicketingsystem.entity.Seat;
import com.example.cinematicketingsystem.entity.Showtime;
import com.example.cinematicketingsystem.entity.ShowtimeSeat;
import com.example.cinematicketingsystem.entity.Ticket;
import com.example.cinematicketingsystem.service.showtime.ShowtimeService;
import com.example.cinematicketingsystem.service.showtimeSeat.ShowtimeSeatService;
import com.example.cinematicketingsystem.service.ticket.TicketService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.mapper.Mapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/cinema")
public class CinemaController {
    private final ShowtimeService showtimeService;
    private final ShowtimeSeatService showtimeSeatService;
    private final TicketService ticketService;

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
}
