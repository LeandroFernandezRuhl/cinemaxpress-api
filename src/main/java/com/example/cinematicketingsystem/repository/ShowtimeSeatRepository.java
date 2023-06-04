package com.example.cinematicketingsystem.repository;

import com.example.cinematicketingsystem.entity.Showtime;
import com.example.cinematicketingsystem.entity.ShowtimeSeat;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShowtimeSeatRepository extends CrudRepository<ShowtimeSeat, Long> {
    List<ShowtimeSeat> findByShowtimeIdAndAvailableTrue(Long showtimeId);
    Optional<ShowtimeSeat> findByIdAndAvailableTrue(Long id);
    Optional<ShowtimeSeat> findByShowtimeAndSeat_RowAndSeat_Column(Showtime showtime, int row, int column);
}
