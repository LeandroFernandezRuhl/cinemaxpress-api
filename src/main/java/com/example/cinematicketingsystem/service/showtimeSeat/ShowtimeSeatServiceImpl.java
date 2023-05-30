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

    public List<SeatDTO> findAvailableSeats(Long showtimeId) {
        log.info("Finding all seats by showtime ID: {}", showtimeId);

        List<ShowtimeSeat> availableSeats = showtimeSeatRepository
                .findByShowtimeIdAndAvailableTrue(showtimeId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No seats available for this showtime"));

        return availableSeats.stream()
                .map(showtimeSeat -> mapper.map(showtimeSeat.getSeat(), SeatDTO.class))
                .collect(Collectors.toList());
    }
}
