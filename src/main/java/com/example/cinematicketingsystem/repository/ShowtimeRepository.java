package com.example.cinematicketingsystem.repository;

import com.example.cinematicketingsystem.entity.Showtime;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ShowtimeRepository extends CrudRepository<Showtime, Long> {
    List<Showtime> findAllByMovieId(Long movieId);
    Optional<Showtime> findByStartTimeAndCinemaRoom_Id(LocalDateTime dateTime, Long id);

    @Query(value = "SELECT * FROM showtimes " +
            "WHERE cinema_room_id = :roomId " +
            "AND (start_time, end_time) OVERLAPS (:newStartTime, :newEndTime)",
            nativeQuery = true)
    List<Showtime> findOverlappingShowtimes(@Param("roomId") Long roomId,
                                   @Param("newStartTime") LocalDateTime newStartTime,
                                   @Param("newEndTime") LocalDateTime newEndTime);
}
