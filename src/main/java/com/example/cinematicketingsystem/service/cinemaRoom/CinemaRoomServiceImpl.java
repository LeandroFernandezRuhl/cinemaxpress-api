package com.example.cinematicketingsystem.service.cinemaRoom;

import com.example.cinematicketingsystem.entity.CinemaRoom;
import com.example.cinematicketingsystem.entity.Showtime;
import com.example.cinematicketingsystem.entity.Ticket;
import com.example.cinematicketingsystem.exception.EntityNotFoundException;
import com.example.cinematicketingsystem.repository.CinemaRoomRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CinemaRoomServiceImpl implements CinemaRoomService {

    CinemaRoomRepository cinemaRoomRepository;

    public CinemaRoomServiceImpl(CinemaRoomRepository cinemaRoomRepository) {
        this.cinemaRoomRepository = cinemaRoomRepository;
    }

    @Override
    public CinemaRoom findById(Long id) {
        log.debug("Finding cinema room with ID = {}", id);
        return cinemaRoomRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(CinemaRoom.class, "id", id.toString()));
    }
}
