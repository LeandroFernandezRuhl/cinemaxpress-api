package com.example.cinematicketingsystem.service.showtimeSeat;

import com.example.cinematicketingsystem.dto.SeatDTO;
import com.example.cinematicketingsystem.entity.Seat;
import com.example.cinematicketingsystem.entity.Showtime;
import com.example.cinematicketingsystem.entity.ShowtimeSeat;
import com.example.cinematicketingsystem.entity.Ticket;

import java.util.List;

public interface ShowtimeSeatService {
    public List<SeatDTO> findAvailableSeats(Long showtimeId);
    public ShowtimeSeat purchaseSeat(Long seatId);
    void refundSeat(Showtime showtime, Ticket ticket);
}
