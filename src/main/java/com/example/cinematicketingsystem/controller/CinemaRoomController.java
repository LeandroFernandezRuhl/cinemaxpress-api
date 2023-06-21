package com.example.cinematicketingsystem.controller;

import com.example.cinematicketingsystem.dto.CreateRoomDTO;
import com.example.cinematicketingsystem.dto.UpdateRoomDTO;
import com.example.cinematicketingsystem.service.cinemaroom.CinemaRoomService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller class for managing cinema rooms.
 * <p>
 * This class handles HTTP requests related to cinema rooms and delegates the business logic to the
 * {@link CinemaRoomService}. It provides endpoints for deleting, saving, and updating cinema rooms.
 * The endpoints are prefixed with "/private/cinema-rooms".
 */
@AllArgsConstructor
@RestController
@RequestMapping("/private/cinema-rooms")
public class CinemaRoomController {
    private CinemaRoomService cinemaRoomService;

    /**
     * Deletes a cinema room with the specified ID.
     *
     * @param id the ID of the cinema room to delete
     * @return a {@link ResponseEntity} with a success message if the cinema room is deleted successfully
     */
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
    @PostMapping()
    public ResponseEntity<String> saveCinemaRoom(@RequestBody @Valid CreateRoomDTO request) {
        cinemaRoomService.saveRoom(request.getHas3d(), request.getHasSurround(), request.getRows(),
                request.getColumns(), request.getPrice());
        return ResponseEntity.ok("Cinema room successfully created");
    }

    /**
     * Updates an existing cinema room based on the provided request data.
     *
     * @param id the id of the room to update
     * @param request the {@link UpdateRoomDTO} object containing the data for updating a cinema room
     * @return a {@link ResponseEntity} with a success message if the cinema room is updated successfully
     */
    @PatchMapping("/{id}")
    public ResponseEntity<String> updateCinemaRoom(@PathVariable("id") Long id, @RequestBody @Valid UpdateRoomDTO request) {
        cinemaRoomService.updateRoom(id, request.getHas3d(), request.getHasSurround(), request.getPrice());
        return ResponseEntity.ok("Cinema room successfully updated");
    }
}
