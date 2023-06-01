package com.example.cinematicketingsystem.service.ticket;

import com.example.cinematicketingsystem.entity.Seat;
import com.example.cinematicketingsystem.entity.Showtime;
import com.example.cinematicketingsystem.entity.Ticket;

public interface TicketService {
    Ticket generateTicket(Seat seat, Showtime showtime);
}
