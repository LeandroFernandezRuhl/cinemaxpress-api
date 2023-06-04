package com.example.cinematicketingsystem.service.ticket;

import com.example.cinematicketingsystem.entity.Seat;
import com.example.cinematicketingsystem.entity.Showtime;
import com.example.cinematicketingsystem.entity.Ticket;
import com.example.cinematicketingsystem.exception.EntityNotFoundException;
import com.example.cinematicketingsystem.repository.TicketRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class TicketServiceImpl implements TicketService {
    TicketRepository ticketRepository;

    public TicketServiceImpl(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @Override
    public Ticket generateTicket(Seat seat, Showtime showtime) {
        log.debug("Generating ticket with seat ID = {} and showtime ID = {}", seat.getId(), showtime.getId());
        Ticket ticket = new Ticket(seat, showtime);
        return ticketRepository.save(ticket);
    }

    @Override
    public void deleteTicket(Ticket ticket) {
        log.debug("Deleting ticket with ID = {}", ticket.getId());
        ticketRepository.delete(ticket);
    }

    @Override
    public Ticket findById(UUID id) {
        log.debug("Finding ticket with ID = {}", id);
        return ticketRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Ticket.class, "id", id.toString()));
    }
}
