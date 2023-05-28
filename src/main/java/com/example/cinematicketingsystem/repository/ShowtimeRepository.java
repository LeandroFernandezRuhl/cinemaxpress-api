package com.example.cinematicketingsystem.repository;

import com.example.cinematicketingsystem.entity.Showtime;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShowtimeRepository extends CrudRepository<Showtime, Long> {
    Optional<List<Showtime>> findAllByMovieId(Long movieId);
}
