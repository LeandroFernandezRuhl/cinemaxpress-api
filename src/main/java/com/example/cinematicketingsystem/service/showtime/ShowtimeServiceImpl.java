package com.example.cinematicketingsystem.service.showtime;

import com.example.cinematicketingsystem.entity.Showtime;
import com.example.cinematicketingsystem.entity.Ticket;
import com.example.cinematicketingsystem.exception.EntityNotFoundException;
import com.example.cinematicketingsystem.repository.ShowtimeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ShowtimeServiceImpl implements ShowtimeService {

    ShowtimeRepository showtimeRepository;

    public ShowtimeServiceImpl(ShowtimeRepository showtimeRepository) {
        this.showtimeRepository = showtimeRepository;
    }

    @Override
    public List<Showtime> findShowtimesByMovieId(Long movieId) {
        log.debug("Finding all showtimes with movie ID = {}", movieId);
        List<Showtime> showtimes = showtimeRepository.findAllByMovieId(movieId);
        if (showtimes.isEmpty()) {
            throw new jakarta.persistence.EntityNotFoundException("No showtimes found with movie ID = "  + movieId);
        }
        return showtimes;
    }

    @Override
    public Showtime findById(Long id) {
        log.debug("Finding showtime with ID = {}", id);
        return showtimeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Showtime.class, "id", id.toString()));
    }

    @Override
    public Showtime findByTicket(Ticket ticket) {
        log.debug("Finding showtime for ticket with ID = {}", ticket.getId());
        return showtimeRepository.findByDateTimeAndCinemaRoom_Id(ticket.getShowtimeDateTime(), ticket.getRoomNumber())
                .orElseThrow(() -> new EntityNotFoundException(Showtime.class, "ticketId", ticket.getId().toString(),
                        "showtimeDateTime", ticket.getShowtimeDateTime().toString()));
    }
}
