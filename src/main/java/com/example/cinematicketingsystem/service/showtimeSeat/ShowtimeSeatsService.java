package com.example.cinematicketingsystem.service.showtimeSeat;

import com.example.cinematicketingsystem.entity.ShowtimeSeat;

import java.util.List;

public interface ShowtimeSeatsService {
    List<ShowtimeSeat> findAvailableSeats(Long showtimeId);
}
