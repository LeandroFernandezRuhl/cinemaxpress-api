package com.example.cinematicketingsystem.controller;

import com.example.cinematicketingsystem.dto.CreateRoomDTO;
import com.example.cinematicketingsystem.dto.UpdateRoomDTO;
import com.example.cinematicketingsystem.service.cinemaRoom.CinemaRoomService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/private/cinema-rooms")
public class CinemaRoomController {
    private CinemaRoomService cinemaRoomService;

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCinemaRoom(@PathVariable("id") Long id) {
        cinemaRoomService.deleteRoom(id);
        return ResponseEntity.ok("Cinema room successfully deleted");
    }

    @PostMapping()
    public ResponseEntity<String> saveCinemaRoom(@RequestBody @Valid CreateRoomDTO request) {
        cinemaRoomService.saveRoom(request.getHas3d(), request.getHasSurround(), request.getRows(),
                request.getColumns(), request.getPrice());
        return ResponseEntity.ok("Cinema room successfully created");
    }

    @PutMapping()
    public ResponseEntity<String> updateCinemaRoom(@RequestBody @Valid UpdateRoomDTO request) {
        cinemaRoomService.updateRoom(request.getId(), request.getHas3d(), request.getHasSurround(), request.getPrice());
        return ResponseEntity.ok("Cinema room successfully updated");
    }
}
