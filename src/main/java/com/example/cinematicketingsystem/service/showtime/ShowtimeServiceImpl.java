package com.example.cinematicketingsystem.service.showtime;

import com.example.cinematicketingsystem.entity.Showtime;
import com.example.cinematicketingsystem.entity.Ticket;
import com.example.cinematicketingsystem.repository.ShowtimeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ShowtimeServiceImpl implements ShowtimeService {

    ShowtimeRepository showtimeRepository;

    public ShowtimeServiceImpl(ShowtimeRepository showtimeRepository) {
        this.showtimeRepository = showtimeRepository;
    }

    @Override
    public List<Showtime> findShowtimesByMovieId(Long movieId) {
        log.info("Finding all showtimes with movie ID: {}", movieId);
        Optional<List<Showtime>> showtimes = showtimeRepository.findAllByMovieId(movieId);

        if (showtimes.isEmpty()) {
            String errorMessage = "No showtimes found with a movie ID of: " + movieId;
            log.error(errorMessage);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, errorMessage);
        }

        return showtimes.get();
    }

    @Override
    public Showtime findById(Long id) {
        log.info("Finding showtime with ID: {}", id);
        Optional<Showtime> optional = showtimeRepository.findById(id);

        if (optional.isEmpty()) {
            String errorMessage = "No showtime found";
            log.error(errorMessage);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, errorMessage);
        }

        return optional.get();
    }

    @Override
    public Showtime findByTicket(Ticket ticket) {
        log.info("Finding showtime for ticket with ID: {}", ticket.getId());
        return showtimeRepository.findByDateTimeAndCinemaRoom_Id(ticket.getShowtimeDateTime(), ticket.getRoomNumber())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Showtime not found for given ticket"));
    }

    //Delete, Save/Update. ?

}
