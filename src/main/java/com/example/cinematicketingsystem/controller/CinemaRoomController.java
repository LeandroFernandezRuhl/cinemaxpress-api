package com.example.cinematicketingsystem.controller;

import com.example.cinematicketingsystem.dto.CreateRoomDTO;
import com.example.cinematicketingsystem.dto.UpdateRoomDTO;
import com.example.cinematicketingsystem.service.cinemaroom.CinemaRoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller class for managing cinema rooms.
 * <p>
 * This class handles HTTP requests related to cinema rooms and delegates the business logic to the
 * {@link CinemaRoomService}. It provides endpoints for deleting, saving, and updating cinema rooms.
 */
@AllArgsConstructor
@Tag(name = "Cinema Rooms", description = "Endpoints for updating, creating and deleting cinema rooms")
@RestController
@RequestMapping("cinema-management/rooms")
public class CinemaRoomController {
    private CinemaRoomService cinemaRoomService;

    /**
     * Deletes a cinema room with the specified ID.
     *
     * @param id the ID of the cinema room to delete
     * @return a {@link ResponseEntity} with a success message if the cinema room is deleted successfully
     */
    @Operation(
            summary = "Delete a cinema room",
            description = "Deletes a cinema room with the specified ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cinema room successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Cinema room not found"),
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCinemaRoom(@PathVariable("id") Long id) {
        cinemaRoomService.deleteRoom(id);
        return ResponseEntity.ok("Cinema room successfully deleted");
    }

    /**
     * Saves a new cinema room based on the provided request data.
     *
     * @param request the {@link CreateRoomDTO} object containing the data for creating a cinema room
     * @return a {@link ResponseEntity} with a success message if the cinema room is saved successfully
     */
    @Operation(
            summary = "Create a cinema room",
            description = "Creates a new cinema room based on the provided request data")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cinema room successfully created"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
    })
    @PostMapping()
    public ResponseEntity<String> saveCinemaRoom(@RequestBody @Valid CreateRoomDTO request) {
        cinemaRoomService.createRoom(request.getHas3d(), request.getHasSurround(), request.getRows(),
                request.getColumns(), request.getPrice());
        return ResponseEntity.ok("Cinema room successfully created");
    }

    /**
     * Updates an existing cinema room based on the provided request data.
     *
     * @param id      the id of the room to update
     * @param request the {@link UpdateRoomDTO} object containing the data for updating a cinema room
     * @return a {@link ResponseEntity} with a success message if the cinema room is updated successfully
     */
    @Operation(
            summary = "Update a cinema room",
            description = "Change the characteristics of the room (like 3D) and the price of its seats")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cinema room successfully updated"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Cinema room not found"),
    })
    @PatchMapping("/{id}")
    public ResponseEntity<String> updateCinemaRoom(@PathVariable("id") Long id, @RequestBody @Valid UpdateRoomDTO request) {
        cinemaRoomService.updateRoom(id, request.getHas3d(), request.getHasSurround(), request.getPrice());
        return ResponseEntity.ok("Cinema room successfully updated");
    }
}