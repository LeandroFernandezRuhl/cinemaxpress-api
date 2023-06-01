package com.example.cinematicketingsystem.service.showtimeSeat;

import com.example.cinematicketingsystem.dto.SeatDTO;
import com.example.cinematicketingsystem.entity.Seat;
import com.example.cinematicketingsystem.entity.ShowtimeSeat;
import com.example.cinematicketingsystem.repository.ShowtimeSeatRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
@Service
public class ShowtimeSeatServiceImpl implements ShowtimeSeatService {

    ShowtimeSeatRepository showtimeSeatRepository;
    ModelMapper mapper;

    @Override
    public List<SeatDTO> findAvailableSeats(Long showtimeId) {
        log.info("Finding all available seats with showtime ID: {}", showtimeId);

        List<ShowtimeSeat> availableSeats = showtimeSeatRepository
                .findByShowtimeIdAndAvailableTrue(showtimeId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No seats available for this showtime"));

        return availableSeats.stream()
                .map(showtimeSeat -> this.convertShowtimeSeatToDTO(showtimeSeat.getSeat()))
                .collect(Collectors.toList());
    }

    private SeatDTO convertShowtimeSeatToDTO(Seat seat) {
        return mapper.map(seat, SeatDTO.class);
    }

    @Override
    public Seat purchaseSeat(Long seatId) {
        log.info("Finding seat with ID: {}", seatId);

        ShowtimeSeat seat = showtimeSeatRepository.findByIdAndAvailableTrue(seatId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Seat doesn't exist or has already been purchased"));

        seat.setAvailable(false);

        return showtimeSeatRepository.save(seat).getSeat();
    }


}
