package com.example.cinematicketingsystem.service.showtime;

import com.example.cinematicketingsystem.entity.CinemaRoom;
import com.example.cinematicketingsystem.entity.Movie;
import com.example.cinematicketingsystem.entity.Showtime;
import com.example.cinematicketingsystem.entity.Ticket;
import com.example.cinematicketingsystem.exception.EntityNotFoundException;
import com.example.cinematicketingsystem.exception.ShowtimeOverlapException;
import com.example.cinematicketingsystem.repository.ShowtimeRepository;
import com.example.cinematicketingsystem.repository.ShowtimeSeatRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@AllArgsConstructor
@Service
public class ShowtimeServiceImpl implements ShowtimeService {
    ShowtimeSeatRepository showtimeSeatRepository;
    ShowtimeRepository showtimeRepository;

    @Override
    public void saveShowtime(Movie movie, CinemaRoom room, LocalDateTime startTime, LocalDateTime endTime) {
        LocalDateTime earliestEndTime = calculateEarliestEndTime(startTime, movie.getDurationInMinutes());

        if (endTime.isBefore(earliestEndTime)) {
            throw new IllegalArgumentException(
                    "The provided end time is too early. Choose a later end time to accommodate the full movie duration");
        }

        List<Showtime> overlappingShowtimes =
                showtimeRepository.findOverlappingShowtimes(room.getId(), startTime, endTime);
        if (overlappingShowtimes.size() > 0) {
            throw new ShowtimeOverlapException("The provided time range overlap with existent showtimes",
                    overlappingShowtimes);
        }
        Showtime newShowtime = new Showtime(startTime, endTime, room, movie);
        showtimeRepository.save(newShowtime);
        newShowtime.initializeAvailableSeats(showtimeSeatRepository);
    }

    public LocalDateTime calculateEarliestEndTime(LocalDateTime startTime, Long movieDuration) {
        return startTime.plusMinutes(movieDuration);
    }

    @Override
    public Showtime findByTicket(Ticket ticket) {
        log.debug("Finding showtime for ticket with ID = {}", ticket.getId());
        return showtimeRepository.findByStartTimeAndCinemaRoom_Id(ticket.getShowtimeStartTime(), ticket.getRoomNumber())
                .orElseThrow(() -> new EntityNotFoundException(Showtime.class, "ticketId", ticket.getId().toString(),
                        "showtimeDateTime", ticket.getShowtimeStartTime().toString()));
    }

    @Override
    public List<Showtime> findShowtimesByMovieId(Long movieId) {
        log.debug("Finding all showtimes with movie ID = {}", movieId);
        List<Showtime> showtimes = showtimeRepository.findAllByMovieId(movieId);
        if (showtimes.isEmpty()) {
            throw new jakarta.persistence.EntityNotFoundException("No showtimes found with movie ID = " + movieId);
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
    @Transactional
    public void deleteShowtime(Long id) {
        log.debug("Deleting showtime with ID = {}", id);
        Showtime showtime = showtimeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Showtime.class, "id", id.toString()));
        CinemaRoom cinemaRoom = showtime.getCinemaRoom();
        cinemaRoom.getShowtimes().remove(showtime);
        showtimeRepository.delete(showtime);
    }
}
