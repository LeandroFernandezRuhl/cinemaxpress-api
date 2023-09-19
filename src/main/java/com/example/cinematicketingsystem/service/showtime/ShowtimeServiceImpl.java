package com.example.cinematicketingsystem.service.showtime;

import com.example.cinematicketingsystem.dto.ShowtimeInfoDTO;
import com.example.cinematicketingsystem.exception.EntityNotFoundException;
import com.example.cinematicketingsystem.exception.ShowtimeOverlapException;
import com.example.cinematicketingsystem.model.CinemaRoom;
import com.example.cinematicketingsystem.model.Movie;
import com.example.cinematicketingsystem.model.Showtime;
import com.example.cinematicketingsystem.model.Ticket;
import com.example.cinematicketingsystem.repository.ShowtimeRepository;
import com.example.cinematicketingsystem.service.cinemaroom.CinemaRoomService;
import com.example.cinematicketingsystem.service.movie.MovieService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service implementation class for managing showtime entities.
 * <p>
 * This class implements the {@link ShowtimeService} interface and provides the functionality to create, retrieve,
 * and delete showtime entities. It handles the business logic associated with showtimes and interacts with the
 * {@link ShowtimeRepository}, {@link MovieService}, {@link CinemaRoomService},
 * and {@link ModelMapper} to perform the necessary operations.
 *
 * @see Showtime
 */
@Slf4j
@AllArgsConstructor
@Service
public class ShowtimeServiceImpl implements ShowtimeService {
    private ShowtimeRepository showtimeRepository;
    private MovieService movieService;
    private CinemaRoomService cinemaRoomService;
    private ModelMapper mapper;

    /**
     * {@inheritDoc}
     *
     * @param startTime the start time of the showtime
     * @param endTime   the end time of the showtime
     * @param movieId   the ID of the movie associated with the showtime
     * @param roomId    the ID of the room where the showtime takes place
     * @return the saved showtime with the generated ID
     * @throws IllegalArgumentException if the provided end time is too early to accommodate the full movie duration
     * @throws IllegalStateException    if the provided start time is in the past
     * @throws ShowtimeOverlapException if the provided time range overlaps with existing showtimes
     * @throws EntityNotFoundException  if the associated cinema room or movie are not found
     */
    @Override
    public Showtime saveShowtime(LocalDateTime startTime, LocalDateTime endTime, Long movieId, Long roomId) {
        log.debug("Creating new showtime");
        Movie movie = movieService.findById(movieId);
        CinemaRoom room = cinemaRoomService.findById(roomId);
        validateShowtime(startTime, endTime, movie.getDurationInMinutes(), room.getId());
        Showtime newShowtime = new Showtime(startTime, endTime, room, movie);
        return showtimeRepository.save(newShowtime);
    }

    /**
     * Validates the provided showtime parameters to ensure they meet the necessary criteria.
     *
     * @param startTime     the start time of the showtime
     * @param endTime       the end time of the showtime
     * @param movieDuration the duration of the movie associated with the showtime
     * @param roomId        the ID of the room where the showtime takes place
     * @throws IllegalArgumentException if the provided end time is too early to accommodate the full movie duration
     * @throws IllegalStateException    if the provided start time is in the past
     * @throws ShowtimeOverlapException if the provided time range overlaps with existing showtimes
     * @throws EntityNotFoundException  if the associated cinema room or movie are not found
     */
    private void validateShowtime(LocalDateTime startTime, LocalDateTime endTime, Long movieDuration, Long roomId) {
        LocalDateTime earliestEndTimePossible = startTime.plusMinutes(movieDuration);
        if (endTime.isBefore(earliestEndTimePossible)) {
            throw new IllegalArgumentException(
                    "The provided end time is too early. Choose a later end time to accommodate the full movie duration");
        }
        if (startTime.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("The provided start time is in the past");
        }
        List<Showtime> overlappingShowtimes = showtimeRepository.findOverlappingShowtimes(roomId, startTime, endTime);
        if (overlappingShowtimes.size() > 0) {
            throw new ShowtimeOverlapException("The provided time range overlaps with existent showtimes",
                    overlappingShowtimes);
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param ticket the ticket for which to find the showtime
     * @return the Showtime object associated with the ticket
     * @throws EntityNotFoundException if the associated showtime is not found
     */
    @Override
    public Showtime findByTicket(Ticket ticket) {
        log.debug("Finding showtime for ticket with ID={}", ticket.getId());
        return showtimeRepository.findByStartTimeAndCinemaRoom_Id(ticket.getShowtimeStartTime(), ticket.getRoomNumber())
                .orElseThrow(() -> new EntityNotFoundException(Showtime.class, "ticketId", ticket.getId().toString(),
                        "showtimeDateTime", ticket.getShowtimeStartTime().toString()));
    }

    /**
     * {@inheritDoc}
     *
     * @param movieId the ID of the movie to retrieve showtimes for
     * @return a list of {@link ShowtimeInfoDTO} objects containing showtime information
     * @throws jakarta.persistence.EntityNotFoundException if no showtimes are found for the specified movie ID
     */
    @Override
    public List<ShowtimeInfoDTO> findShowtimesByMovieId(Long movieId) {
        log.debug("Finding all showtimes with movie ID={}", movieId);
        List<Showtime> showtimes = showtimeRepository.findAllByMovieIdAndEndTimeAfter(movieId, LocalDateTime.now());
        if (showtimes.isEmpty()) {
            throw new jakarta.persistence.EntityNotFoundException("No showtimes found with movie ID = " + movieId);
        }
        return showtimes.stream().map(this::convertShowtimeToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Converts a Showtime entity to a {@link ShowtimeInfoDTO} object.
     *
     * @param showtime the Showtime entity to be converted
     * @return the converted {@link ShowtimeInfoDTO} object
     */
    private ShowtimeInfoDTO convertShowtimeToDTO(Showtime showtime) {
        ShowtimeInfoDTO showtimeInfoDTO = mapper.map(showtime, ShowtimeInfoDTO.class);
        CinemaRoom room = showtime.getCinemaRoom();
        showtimeInfoDTO.setRoomHas3d(room.getHas3d());
        showtimeInfoDTO.setRoomHasSurround(room.getHasSurround());
        return showtimeInfoDTO;
    }

    /**
     * {@inheritDoc}
     *
     * @param id the ID of the showtime to retrieve
     * @return the Showtime object with the specified ID
     * @throws EntityNotFoundException if the showtime with the specified ID is not found
     */
    @Override
    public Showtime findById(Long id) {
        log.debug("Finding showtime with ID={}", id);
        return showtimeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Showtime.class, "id", id.toString()));
    }

    /**
     * {@inheritDoc}
     *
     * @param id the ID of the showtime to delete
     * @throws EntityNotFoundException if the showtime with the specified ID is not found
     */
    @Override
    @Transactional
    public void deleteShowtime(Long id) {
        log.debug("Deleting showtime with ID={}", id);
        Showtime showtime = showtimeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Showtime.class, "id", id.toString()));
        CinemaRoom cinemaRoom = showtime.getCinemaRoom();
        cinemaRoom.getShowtimes().remove(showtime);
        showtimeRepository.delete(showtime);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void deleteFinishedShowtimes() {
        log.debug("Deleting all finished showtimes");
        List<Showtime> finishedShowtimes = showtimeRepository.findAllByEndTimeBefore(LocalDateTime.now());
        for (Showtime showtime : finishedShowtimes) {
            this.deleteShowtime(showtime.getId());
        }
    }
}
