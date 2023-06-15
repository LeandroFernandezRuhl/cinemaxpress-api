package com.example.cinematicketingsystem.service.cinemaRoom;

import com.example.cinematicketingsystem.entity.CinemaRoom;
import com.example.cinematicketingsystem.exception.EntityNotFoundException;
import com.example.cinematicketingsystem.repository.CinemaRoomRepository;
import com.example.cinematicketingsystem.repository.SeatRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@AllArgsConstructor
@Service
public class CinemaRoomServiceImpl implements CinemaRoomService {

    CinemaRoomRepository cinemaRoomRepository;
    SeatRepository seatRepository;

    @Override
    public void saveRoom(Boolean has3d, Boolean hasSurround, Integer rows, Integer columns, Double seatPrice) {
        CinemaRoom room = new CinemaRoom(has3d, hasSurround, rows, columns, seatPrice);
        cinemaRoomRepository.save(room);
        room.initializeSeats(seatRepository);
    }

    @Override
    public void updateRoom(Long id, Boolean has3d, Boolean hasSurround, Double seatPrice) {
        CinemaRoom room = this.findById(id);
        room.setHas3d(has3d);
        room.setHasSurround(hasSurround);
        room.setBaseSeatPrice(seatPrice);
        room.getSeats().forEach(seat -> seat.setPrice(seatPrice));
        cinemaRoomRepository.save(room);
    }

    @Override
    public void deleteRoom(Long id) {
        log.debug("Deleting room with ID = {}", id);
        CinemaRoom room = cinemaRoomRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(CinemaRoom.class, "id", id.toString()));
        cinemaRoomRepository.delete(room);
    }

    @Override
    public CinemaRoom findById(Long id) {
        log.debug("Finding cinema room with ID = {}", id);
        return cinemaRoomRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(CinemaRoom.class, "id", id.toString()));
    }
}
