package com.leandroruhl.cinemaxpress.service.cinemaroom;

import com.leandroruhl.cinemaxpress.exception.EntityNotFoundException;
import com.leandroruhl.cinemaxpress.model.CinemaRoom;
import com.leandroruhl.cinemaxpress.repository.CinemaRoomRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Service implementation for managing cinema room entities.
 * <p>
 * This implementation class provides the actual implementation for the methods defined in the {@link CinemaRoomService}
 * interface. It uses the {@link CinemaRoomRepository} to interact with the underlying database and perform the necessary operations.
 *
 * @see CinemaRoom
 */
@Slf4j
@AllArgsConstructor
@Service
public class CinemaRoomServiceImpl implements CinemaRoomService {
    private CinemaRoomRepository cinemaRoomRepository;

    /**
     * {@inheritDoc}
     *
     * @param has3d       true if the cinema room has 3D capability, false otherwise
     * @param hasSurround true if the cinema room has surround sound, false otherwise
     * @param rows        the number of rows in the cinema room
     * @param columns     the number of columns in the cinema room
     * @param seatPrice   the base price for each seat in the cinema room
     * @return the newly saved cinema room
     */
    @Override
    public CinemaRoom createRoom(Boolean has3d, Boolean hasSurround, Integer rows, Integer columns, Double seatPrice) {
        log.debug("Creating new room");
        CinemaRoom room = new CinemaRoom(has3d, hasSurround, rows, columns, seatPrice);
        return cinemaRoomRepository.save(room);
    }

    /**
     * {@inheritDoc}
     *
     * @param id          the ID of the cinema room to update
     * @param has3d       true if the cinema room has 3D capability, false otherwise
     * @param hasSurround true if the cinema room has surround sound, false otherwise
     * @param seatPrice   the new base price for each seat in the cinema room
     * @throws EntityNotFoundException if the cinema room with the specified ID is not found
     */
    @Override
    public void updateRoom(Long id, Boolean has3d, Boolean hasSurround, Double seatPrice) {
        log.debug("Updating room with ID={}", id);
        CinemaRoom room = this.findById(id);
        room.setHas3d(has3d);
        room.setHasSurround(hasSurround);
        room.setBaseSeatPrice(seatPrice);
        room.getSeats().forEach(seat -> seat.setPrice(seatPrice));
        cinemaRoomRepository.save(room);
    }

    /**
     * {@inheritDoc}
     *
     * @param id the ID of the cinema room to delete
     * @throws EntityNotFoundException if the cinema room with the specified ID is not found
     */
    @Override
    public void deleteRoom(Long id) {
        log.debug("Deleting room with ID={}", id);
        CinemaRoom room = this.findById(id);
        cinemaRoomRepository.delete(room);
    }

    /**
     * {@inheritDoc}
     *
     * @param id the ID of the cinema room
     * @return the cinema room with the specified ID
     * @throws EntityNotFoundException if the cinema room with the specified ID is not found
     */
    @Override
    public CinemaRoom findById(Long id) {
        log.debug("Finding cinema room with ID={}", id);
        return cinemaRoomRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(CinemaRoom.class, "id", id.toString()));
    }
}
