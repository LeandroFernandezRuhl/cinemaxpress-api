package com.leandroruhl.cinemaxpress.controller;

import com.leandroruhl.cinemaxpress.dto.TicketInfoDTO;
import com.leandroruhl.cinemaxpress.service.ticket.TicketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

/**
 * Controller class for retrieving analytics.
 * <p>
 * This class handles HTTP requests related to analytics and delegates the retrieval of metrics
 * to the {@link TicketService}. It provides endpoints for retrieving analytics based on a time range
 * and a specific movie.
 */
@AllArgsConstructor
@Tag(name = "Analytics", description = "Endpoints for retrieving analytics, such as the number of sold tickets")
@RestController
@RequestMapping("/cinema-management/analytics")

public class AnalyticsController {
    private TicketService ticketService;

    /**
     * Retrieves analytics within a specified time range.
     *
     * @param from the start date of the time range
     * @param to   the end date of the time range
     * @return a {@link ResponseEntity} containing the {@link TicketInfoDTO} with the ticket statistics
     */
    @Operation(
            summary = "Retrieve global analytics within a specified time range",
            description = "Get analytics within the specified time range. Returns a TicketInfoDTO with statistics"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved ticket statistics"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
    })
    @GetMapping("/time-range")
    public ResponseEntity<TicketInfoDTO> getTicketStats(
            @RequestParam("from") LocalDate from,
            @RequestParam("to") LocalDate to) {
        TicketInfoDTO ticketStats = ticketService.getTicketStatsByTimeRange(from, to);
        return ResponseEntity.ok(ticketStats);
    }

    /**
     * Retrieves analytics for a specific movie.
     *
     * @param title the title of the movie
     * @return a {@link ResponseEntity} containing the {@link TicketInfoDTO} with the ticket statistics
     */
    @Operation(
            summary = "Retrieve analytics for a specific movie",
            description = "Get analytics for the specified movie title. Returns a TicketInfoDTO with statistics"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved ticket statistics"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
    })
    @GetMapping("/{movie-title}")
    public ResponseEntity<TicketInfoDTO> getTicketStats(@PathVariable("movie-title") String title) {
        TicketInfoDTO ticketStats = ticketService.getTicketStatsByMovieTitle(title);
        return ResponseEntity.ok(ticketStats);
    }
}
