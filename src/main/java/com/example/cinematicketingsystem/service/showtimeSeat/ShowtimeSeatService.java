package com.example.cinematicketingsystem.service.showtimeSeat;

import com.example.cinematicketingsystem.dto.SeatDTO;
import com.example.cinematicketingsystem.entity.Seat;
import com.example.cinematicketingsystem.entity.ShowtimeSeat;

import java.util.List;

public interface ShowtimeSeatService {
    public List<SeatDTO> findAvailableSeats(Long showtimeId);
    public Seat purchaseSeat(Long seatId);
}
