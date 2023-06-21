package com.example.cinematicketingsystem.service.showtimeSeat;

import com.example.cinematicketingsystem.dto.SeatInfoDTO;
import com.example.cinematicketingsystem.entity.Seat;
import com.example.cinematicketingsystem.entity.Showtime;
import com.example.cinematicketingsystem.entity.ShowtimeSeat;
import com.example.cinematicketingsystem.entity.Ticket;
import com.example.cinematicketingsystem.exception.EntityNotFoundException;
import com.example.cinematicketingsystem.repository.ShowtimeSeatRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of the {@link ShowtimeSeatService} interface that provides methods for managing showtime seats.
 * <p>
 * This implementation uses the {@link ShowtimeSeatRepository} for accessing and persisting showtime seat data, and
 * the {@link ModelMapper} for mapping between entities and DTOs.
 *
 * @see ShowtimeSeat
 */
@Slf4j
@AllArgsConstructor
@Service
public class ShowtimeSeatServiceImpl implements ShowtimeSeatService {
    private ShowtimeSeatRepository showtimeSeatRepository;
    private ModelMapper mapper;

    /**
     * {@inheritDoc}
     *
     * @param showtimeId the ID of the showtime to find available seats for
     * @return a list of {@link SeatInfoDTO} objects representing the available seats
     * @throws EntityNotFoundException if no available seats are found for the specified showtime ID
     */
    @Override
    public List<SeatInfoDTO> findAvailableSeats(Long showtimeId) {
        log.debug("Finding all available seats with showtime ID={}", showtimeId);
        List<ShowtimeSeat> availableSeats = showtimeSeatRepository.findByShowtimeIdAndAvailableTrue(showtimeId);
        if (availableSeats.isEmpty()) {
            throw new EntityNotFoundException(ShowtimeSeat.class, "id", showtimeId.toString());
        }
        return availableSeats.stream().map(this::convertShowtimeSeatToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Converts a ShowtimeSeat entity to a {@link SeatInfoDTO} object.
     *
     * @param showtimeSeat the ShowtimeSeat entity to be converted
     * @return the converted {@link SeatInfoDTO} object
     */
    private SeatInfoDTO convertShowtimeSeatToDTO(ShowtimeSeat showtimeSeat) {
        Seat seat = showtimeSeat.getSeat();
        SeatInfoDTO seatInfoDTO = mapper.map(seat, SeatInfoDTO.class);
        seatInfoDTO.setId(showtimeSeat.getId());
        return seatInfoDTO;
    }

    /**
     * {@inheritDoc}
     *
     * @param seatId the ID of the seat to purchase
     * @return the ShowtimeSeat object representing the purchased seat
     * @throws EntityNotFoundException if no available seat is found for the specified seat ID
     * @throws IllegalStateException   if the showtime associated with the seat has already ended
     */
    @Override
    public ShowtimeSeat purchaseSeat(Long seatId) {
        log.debug("Finding seat with ID={}", seatId);
        ShowtimeSeat seat = showtimeSeatRepository.findByIdAndAvailableTrue(seatId)
                .orElseThrow(() -> new EntityNotFoundException(ShowtimeSeat.class, "id", seatId.toString()));
        if (seat.getShowtime().getEndTime().isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("Cannot purchase a seat for a showtime that has already ended");
        }
        seat.setAvailable(false);
        return showtimeSeatRepository.save(seat);
    }

    /**
     * {@inheritDoc}
     *
     * @param showtime the showtime associated with the seat
     * @param ticket   the ticket associated with the seat
     * @throws EntityNotFoundException if no seat is found for the specified showtime, row, and column
     * @throws IllegalStateException if the client tries to refund a ticket for a showtime that has already started
     */
    @Override
    public void refundSeat(Showtime showtime, Ticket ticket) {

        log.debug("Refunding ticket with ID={}", ticket.getId());
        int row = ticket.getRow();
        int column = ticket.getColumn();
        ShowtimeSeat seat = showtimeSeatRepository.findByShowtimeAndSeat_RowAndSeat_Column(showtime, row, column)
                .orElseThrow(() -> new EntityNotFoundException(ShowtimeSeat.class, "id", showtime.getId().toString(),
                        "row", Integer.toString(row), "column", Integer.toString(column)));
        if (showtime.getStartTime().isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("Cannot refund a ticket for a showtime that has already started");
        }
        seat.setAvailable(true);
        showtimeSeatRepository.save(seat);
    }
}
