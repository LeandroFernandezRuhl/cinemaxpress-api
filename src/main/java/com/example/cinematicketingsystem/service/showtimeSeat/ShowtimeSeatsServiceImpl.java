package com.example.cinematicketingsystem.service.showtimeSeat;

import com.example.cinematicketingsystem.entity.Showtime;
import com.example.cinematicketingsystem.entity.ShowtimeSeat;
import com.example.cinematicketingsystem.repository.ShowtimeSeatRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ShowtimeSeatsServiceImpl implements ShowtimeSeatsService {

    ShowtimeSeatRepository showtimeSeatRepository;

    public ShowtimeSeatsServiceImpl(ShowtimeSeatRepository showtimeSeatRepository) {
        this.showtimeSeatRepository = showtimeSeatRepository;
    }

    public List<ShowtimeSeat> findAvailableSeats(Long showtimeId) {
        log.info("Finding all seats by showtime ID: {}", showtimeId);
        Optional<List<ShowtimeSeat>> seats = showtimeSeatRepository.findByShowtimeIdAndAvailableTrue(showtimeId);

        if (seats.isEmpty()) {
            String errorMessage = "No seats available for this showtime";
            log.error(errorMessage);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, errorMessage);
        }

        return seats.get();
    }

}
