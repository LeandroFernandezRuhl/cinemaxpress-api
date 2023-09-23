package com.example.cinematicketingsystem.controller;

import com.example.cinematicketingsystem.dto.CreateShowtimeDTO;
import com.example.cinematicketingsystem.model.Showtime;
import com.example.cinematicketingsystem.service.showtime.ShowtimeService;
import com.example.cinematicketingsystem.service.ticket.TicketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * Controller class for managing showtimes.
 * <p>
 * This class handles API requests related to showtimes. It provides endpoints for deleting showtimes,
 * deleting finished showtimes, and saving new showtimes.
 * @see ShowtimeService
 * @see TicketService
 */
@AllArgsConstructor
@Tag(name = "Showtimes", description = "Endpoints for scheduling showtimes")
@RestController
@RequestMapping("/cinema-management/showtimes")
public class ShowtimeController {
    private ShowtimeService showtimeService;
    private TicketService ticketService;

    /**
     * Deletes a showtime specified by its ID.
     * If the showtime hasn't started yet, it refunds the tickets associated with the showtime before deletion.
     *
     * @param id the ID of the showtime to be deleted
     * @return a {@link ResponseEntity} with a success message
     */
    @Operation(
            summary = "Cancel a showtime",
            description = "Deletes a showtime specified by its ID. If the showtime hasn't started yet, it refunds the tickets associated with the showtime before deletion"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Showtime successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Showtime not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteShowtime(@PathVariable("id") Long id) {
        Showtime showtime = showtimeService.findById(id);
        if (LocalDateTime.now().isBefore(showtime.getStartTime())) {
            ticketService.deleteTicketsByShowtime(showtime);
        }
        showtimeService.deleteShowtime(id);
        return ResponseEntity.ok("Showtime successfully deleted");
    }

    /**
     * Deletes finished showtimes.
     * This endpoint deletes all showtimes that have already ended.
     *
     * @return a {@link ResponseEntity} with a success message
     */
    @Operation(
            summary = "Delete finished showtimes",
            description = "Deletes finished showtimes. This endpoint deletes all showtimes that have already ended"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Finished showtimes successfully deleted"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping()
    public ResponseEntity<String> deleteFinishedShowtimes() {
        showtimeService.deleteFinishedShowtimes();
        return ResponseEntity.ok("Finished showtimes successfully deleted");
    }

    /**
     * Saves a new showtime based on the provided details.
     *
     * @param request the {@link CreateShowtimeDTO} containing the details of the showtime to be saved
     * @return a {@link ResponseEntity} with a success message
     */
    @Operation(
            summary = "Schedule a showtime",
            description = "Saves a new showtime based on the provided details"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Showtime successfully saved"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping()
    public ResponseEntity<String> saveShowtime(@RequestBody @Valid CreateShowtimeDTO request) {
        showtimeService.saveShowtime(request.getStartTime(), request.getEndTime(), request.getMovieId(),
                request.getRoomId());
        return ResponseEntity.ok("Showtime successfully saved");
    }
}
