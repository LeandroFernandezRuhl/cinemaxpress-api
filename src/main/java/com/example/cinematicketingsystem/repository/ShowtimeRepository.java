package com.example.cinematicketingsystem.repository;

import com.example.cinematicketingsystem.entity.Showtime;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShowtimeRepository extends CrudRepository<Showtime, Long> {
}
