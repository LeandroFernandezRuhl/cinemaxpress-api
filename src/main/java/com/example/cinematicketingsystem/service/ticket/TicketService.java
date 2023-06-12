package com.example.cinematicketingsystem.service.ticket;

import com.example.cinematicketingsystem.entity.Seat;
import com.example.cinematicketingsystem.entity.Showtime;
import com.example.cinematicketingsystem.entity.Ticket;

import java.util.UUID;

public interface TicketService {
    Ticket generateTicket(Seat seat, Showtime showtime);
    void deleteTicket(Ticket ticket);
    Ticket findById(UUID id);
    void deleteTicketsByShowtime(Showtime showtime);
}
