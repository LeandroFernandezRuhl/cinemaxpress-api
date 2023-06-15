package com.example.cinematicketingsystem.service.cinemaRoom;

import com.example.cinematicketingsystem.entity.CinemaRoom;


public interface CinemaRoomService {
    CinemaRoom findById(Long id);
    void saveRoom(Boolean has3d, Boolean hasSurround, Integer rows, Integer columns, Double seatPrice);
    void updateRoom(Long id, Boolean has3d, Boolean hasSurround, Double seatPrice);
    void deleteRoom(Long id);
}
