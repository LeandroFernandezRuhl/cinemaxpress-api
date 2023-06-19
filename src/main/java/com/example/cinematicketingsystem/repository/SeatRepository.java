package com.example.cinematicketingsystem.repository;

import com.example.cinematicketingsystem.entity.Seat;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing seat entities in the database.
 * <p>
 * This interface extends the Spring Data `CrudRepository` interface, which provides generic CRUD (Create, Read,
 * Update, Delete) operations for the `Seat` entity.
 *
 * @see Seat
 * @see org.springframework.data.repository.CrudRepository
 */
@Repository
public interface SeatRepository extends CrudRepository<Seat, Long> {
}
