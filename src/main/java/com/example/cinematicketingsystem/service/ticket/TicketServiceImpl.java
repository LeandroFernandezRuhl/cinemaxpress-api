package com.example.cinematicketingsystem.service.ticket;

import com.example.cinematicketingsystem.entity.Seat;
import com.example.cinematicketingsystem.entity.Showtime;
import com.example.cinematicketingsystem.entity.Ticket;
import com.example.cinematicketingsystem.repository.TicketRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TicketServiceImpl implements TicketService {
    TicketRepository ticketRepository;

    public TicketServiceImpl(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @Override
    public Ticket generateTicket(Seat seat, Showtime showtime) {
        log.info("Generating ticket");
        Ticket ticket = new Ticket(seat, showtime);
        return ticketRepository.save(ticket);
    }
}
