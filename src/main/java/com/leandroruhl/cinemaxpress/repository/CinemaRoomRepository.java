package com.leandroruhl.cinemaxpress.repository;

import com.leandroruhl.cinemaxpress.model.CinemaRoom;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing cinema room entities in the database.
 * <p>
 * This interface extends the Spring Data `CrudRepository` interface, which provides generic CRUD (Create, Read,
 * Update, Delete) operations for the `CinemaRoom` entity.
 *
 * @see CinemaRoom
 * @see org.springframework.data.repository.CrudRepository
 */
@Repository
public interface CinemaRoomRepository extends CrudRepository<CinemaRoom, Long> {
}
