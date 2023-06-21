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

/**
 * Controller class for retrieving ticket statistics.
 * <p>
 * This class handles HTTP requests related to ticket statistics and delegates the retrieval of metrics
 * to the {@link TicketService}. It provides endpoints for retrieving ticket statistics based on a time range
 * and a specific movie. The endpoints are prefixed with "/private/metrics".
 */
@AllArgsConstructor
@RestController
@RequestMapping("/private/metrics")
public class MetricsController {
    private TicketService ticketService;

    /**
     * Retrieves ticket statistics within a specified time range.
     *
     * @param from the start date of the time range
     * @param to   the end date of the time range
     * @return a {@link ResponseEntity} containing the {@link TicketInfoDTO} with the ticket statistics
     */
    @GetMapping("time-range")
    public ResponseEntity<TicketInfoDTO> getTicketStats(
            @RequestParam("from") LocalDate from,
            @RequestParam("to") LocalDate to) {
        TicketInfoDTO ticketStats = ticketService.getTicketStatsByTimeRange(from, to);
        return ResponseEntity.ok(ticketStats);
    }

    /**
     * Retrieves ticket statistics for a specific movie.
     *
     * @param title the title of the movie
     * @return a {@link ResponseEntity} containing the {@link TicketInfoDTO} with the ticket statistics
     */
    @GetMapping("movie")
    public ResponseEntity<TicketInfoDTO> getTicketStats(@RequestParam("title") String title) {
        TicketInfoDTO ticketStats = ticketService.getTicketStatsByMovieTitle(title);
        return ResponseEntity.ok(ticketStats);
    }
}
