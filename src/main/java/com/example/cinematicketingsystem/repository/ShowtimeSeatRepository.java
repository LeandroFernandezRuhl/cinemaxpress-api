package com.example.cinematicketingsystem.repository;

import com.example.cinematicketingsystem.entity.Showtime;
import com.example.cinematicketingsystem.entity.ShowtimeSeat;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing showtime seat entities in the database.
 * <p>
 * This interface extends the Spring Data `CrudRepository` interface, which provides generic CRUD (Create, Read,
 * Update, Delete) operations for the `ShowtimeSeat` entity. In addition to the default CRUD methods, this repository
 * interface defines several custom query methods for retrieving showtime seats based on specific criteria.
 *
 * @see ShowtimeSeat
 * @see org.springframework.data.repository.CrudRepository
 */
@Repository
public interface ShowtimeSeatRepository extends CrudRepository<ShowtimeSeat, Long> {
    /**
     * Retrieves all available showtime seats for a given showtime ID.
     *
     * @param showtimeId the ID of the showtime
     * @return a list of available showtime seats for the showtime
     */
    List<ShowtimeSeat> findByShowtimeIdAndAvailableTrue(Long showtimeId);

    /**
     * Retrieves an available showtime seat with the specified ID.
     *
     * @param id the ID of the showtime seat
     * @return an optional containing the available showtime seat, or empty if not found
     */
    Optional<ShowtimeSeat> findByIdAndAvailableTrue(Long id);

    /**
     * Retrieves an available showtime seat for a given showtime, row, and column.
     *
     * @param showtime the showtime
     * @param row      the row of the seat
     * @param column   the column of the seat
     * @return an optional containing the available showtime seat, or empty if not found
     */
    Optional<ShowtimeSeat> findByShowtimeAndSeat_RowAndSeat_Column(Showtime showtime, int row, int column);
}
