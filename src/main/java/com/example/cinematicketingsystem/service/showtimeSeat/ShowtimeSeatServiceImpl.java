package com.example.cinematicketingsystem.service.showtimeSeat;

import com.example.cinematicketingsystem.dto.SeatDTO;
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

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
@Service
public class ShowtimeSeatServiceImpl implements ShowtimeSeatService {

    ShowtimeSeatRepository showtimeSeatRepository;
    ModelMapper mapper;

    @Override
    public List<SeatDTO> findAvailableSeats(Long showtimeId) {
        log.debug("Finding all available seats with showtime ID = {}", showtimeId);
        List<ShowtimeSeat> availableSeats = showtimeSeatRepository.findByShowtimeIdAndAvailableTrue(showtimeId);
        if (availableSeats.isEmpty()) {
            throw new jakarta.persistence.EntityNotFoundException("No available seats found with showtime ID " + showtimeId);
        }
        return availableSeats.stream().map(showtimeSeat -> this.convertShowtimeSeatToDTO(showtimeSeat.getSeat()))
                .collect(Collectors.toList());
    }

    private SeatDTO convertShowtimeSeatToDTO(Seat seat) {
        return mapper.map(seat, SeatDTO.class);
    }

    @Override
    public ShowtimeSeat purchaseSeat(Long seatId) {
        log.debug("Finding seat with ID = {}", seatId);
        ShowtimeSeat seat = showtimeSeatRepository.findByIdAndAvailableTrue(seatId)
                .orElseThrow(() -> new EntityNotFoundException(ShowtimeSeat.class, "id", seatId.toString(),
                        "available", "true"));
        seat.setAvailable(false);
        return showtimeSeatRepository.save(seat);
    }

    @Override
    public void refundSeat(Showtime showtime, Ticket ticket) {
        log.debug("Refunding ticket with ID = {}", ticket.getId());
        int row = ticket.getRow();
        int column = ticket.getColumn();
        ShowtimeSeat seat = showtimeSeatRepository.findByShowtimeAndSeat_RowAndSeat_Column(showtime, row, column)
                .orElseThrow(() -> new EntityNotFoundException(ShowtimeSeat.class, "id", showtime.getId().toString(),
                                "row", Integer.toString(row), "column", Integer.toString(column)));
        seat.setAvailable(true);
        showtimeSeatRepository.save(seat);
    }
}
