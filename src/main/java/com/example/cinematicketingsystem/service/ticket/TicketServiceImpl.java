package com.example.cinematicketingsystem.service.ticket;

import com.example.cinematicketingsystem.dto.TicketInfoDTO;
import com.example.cinematicketingsystem.entity.Seat;
import com.example.cinematicketingsystem.entity.Showtime;
import com.example.cinematicketingsystem.entity.Ticket;
import com.example.cinematicketingsystem.exception.EntityNotFoundException;
import com.example.cinematicketingsystem.repository.TicketRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * Implementation of the {@link TicketService} interface for managing tickets.
 * <p>
 * This class provides the implementation of the ticket management operations, such as generating tickets, deleting
 * tickets, and retrieving ticket statistics. It uses the {@link TicketRepository} for data access.
 *
 * @see Ticket
 */
@Slf4j
@AllArgsConstructor
@Service
public class TicketServiceImpl implements TicketService {
    private TicketRepository ticketRepository;

    /**
     * {@inheritDoc}
     *
     * @param from the starting date of the time range
     * @param to   the ending date of the time range
     * @return a {@link TicketInfoDTO} object containing the ticket statistics for the specified time range
     */
    @Override
    public TicketInfoDTO getTicketStatsByTimeRange(LocalDate from, LocalDate to) {
        log.debug("Getting ticket stats from {} to {}", from, to);
        List<Ticket> tickets = ticketRepository.findByIssueDateTimeIsBetween(from.atStartOfDay(), to.atStartOfDay());
        return listToTicketDTO(tickets);
    }

    /**
     * {@inheritDoc}
     *
     * @param id the ID of the movie for which to retrieve ticket statistics
     * @return a {@link TicketInfoDTO} object containing the ticket statistics for the specified movie ID
     */
    @Override
    public TicketInfoDTO getTicketStatsByMovieId(Long id) {
        log.debug("Getting ticket stats for the movie ID={}", id);
        List<Ticket> tickets = ticketRepository.findByMovieId(id);
        return listToTicketDTO(tickets);
    }

    /**
     * {@inheritDoc}
     *
     * @param tickets the list of tickets to convert
     * @return a {@link TicketInfoDTO} object containing ticket statistics
     */
    private TicketInfoDTO listToTicketDTO(List<Ticket> tickets) {
        Integer numberOfSoldTickets = tickets.size();
        Double totalMoneyGenerated = tickets.stream().mapToDouble(Ticket::getPrice).sum();
        return new TicketInfoDTO(numberOfSoldTickets, totalMoneyGenerated);
    }

    /**
     * {@inheritDoc}
     *
     * @param seat     the seat for which to generate the ticket
     * @param showtime the showtime for which to generate the ticket
     * @return the generated ticket object
     */
    @Override
    public Ticket generateTicket(Seat seat, Showtime showtime) {
        log.debug("Generating ticket with seat ID={} and showtime ID={}", seat.getId(), showtime.getId());
        Ticket ticket = new Ticket(seat, showtime);
        return ticketRepository.save(ticket);
    }

    /**
     * {@inheritDoc}
     *
     * @param showtime the showtime for which to delete tickets
     */
    @Override
    @Transactional
    public void deleteTicketsByShowtime(Showtime showtime) {
        log.debug("Deleting tickets bought for the showtime ID={}", showtime.getId());
        ticketRepository.deleteAllByShowtimeStartTime(showtime.getStartTime());
    }

    /**
     * {@inheritDoc}
     *
     * @param ticket the ticket to delete
     */
    @Override
    public void deleteTicket(Ticket ticket) {
        log.debug("Deleting ticket with ID={}", ticket.getId());
        ticketRepository.delete(ticket);
    }

    /**
     * {@inheritDoc}
     *
     * @param id the UUID of the ticket to retrieve
     * @return the {@link Ticket} object with the specified UUID
     * @throws EntityNotFoundException if no ticket with the specified UUID is found
     */
    @Override
    public Ticket findById(UUID id) {
        log.debug("Finding ticket with ID={}", id);
        return ticketRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Ticket.class, "id", id.toString()));
    }
}
