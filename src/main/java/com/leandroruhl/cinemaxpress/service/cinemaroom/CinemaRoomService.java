package com.leandroruhl.cinemaxpress.service.cinemaroom;

import com.leandroruhl.cinemaxpress.model.CinemaRoom;

/**
 * Service interface for managing cinema room entities.
 * <p>
 * The `CinemaRoomService` interface defines methods for retrieving, creating, updating, and deleting cinema rooms.
 *
 * @see CinemaRoom
 */
public interface CinemaRoomService {
    /**
     * Retrieves a cinema room by its ID.
     *
     * @param id the ID of the cinema room
     * @return the cinema room with the specified ID
     */
    CinemaRoom findById(Long id);

    /**
     * Creates a new cinema room with the specified details.
     *
     * @param has3d       true if the cinema room has 3D capability, false otherwise
     * @param hasSurround true if the cinema room has surround sound, false otherwise
     * @param rows        the number of rows in the cinema room
     * @param columns     the number of columns in the cinema room
     * @param seatPrice   the base price for each seat in the cinema room
     * @return the newly saved cinema room
     */
    CinemaRoom createRoom(Boolean has3d, Boolean hasSurround, Integer rows, Integer columns, Double seatPrice);

    /**
     * Updates an existing cinema room with the specified details.
     *
     * @param id          the ID of the cinema room to update
     * @param has3d       true if the cinema room has 3D capability, false otherwise
     * @param hasSurround true if the cinema room has surround sound, false otherwise
     * @param seatPrice   the new base price for each seat in the cinema room
     */
    void updateRoom(Long id, Boolean has3d, Boolean hasSurround, Double seatPrice);

    /**
     * Deletes a cinema room with the specified ID.
     *
     * @param id the ID of the cinema room to delete
     */
    void deleteRoom(Long id);
}
