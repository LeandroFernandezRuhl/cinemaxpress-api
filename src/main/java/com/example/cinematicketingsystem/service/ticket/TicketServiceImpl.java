package com.example.cinematicketingsystem.service.ticket;

import com.example.cinematicketingsystem.entity.Seat;
import com.example.cinematicketingsystem.entity.Showtime;
import com.example.cinematicketingsystem.entity.Ticket;
import com.example.cinematicketingsystem.repository.TicketRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
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
        log.info("Generating ticket");
        Ticket ticket = new Ticket(seat, showtime);
        return ticketRepository.save(ticket);
    }

    @Override
    public void deleteTicket(Ticket ticket) {
        log.info("Deleting ticket with ID: {}", ticket.getId());
        ticketRepository.delete(ticket);
    }

    @Override
    public Ticket findById(UUID id) {
        log.info("Finding ticket with ID: {}", id);
        Optional<Ticket> optional = ticketRepository.findById(id);

        if (optional.isEmpty()) {
            String errorMessage = "No ticket found";
            log.error(errorMessage);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, errorMessage);
        }

        return optional.get();
    }
}
