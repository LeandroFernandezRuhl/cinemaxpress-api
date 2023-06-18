package com.example.cinematicketingsystem.controller;

import com.example.cinematicketingsystem.dto.TicketInfoDTO;
import com.example.cinematicketingsystem.service.ticket.TicketService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@AllArgsConstructor
@RestController
@RequestMapping("/private/metrics")
public class MetricsController {
    TicketService ticketService;

    @GetMapping("time-range")
    public ResponseEntity<TicketInfoDTO> getTicketStats(
            @RequestParam("from") LocalDate from,
            @RequestParam("to") LocalDate to) {
        TicketInfoDTO ticketStats = ticketService.getTicketStatsByTimeRange(from, to);
        return ResponseEntity.ok(ticketStats);
    }

    @GetMapping("movie")
    public ResponseEntity<TicketInfoDTO> getTicketStats(@RequestParam("id") Long id) {
        TicketInfoDTO ticketStats = ticketService.getTicketStatsByMovieId(id);
        return ResponseEntity.ok(ticketStats);
    }
}
