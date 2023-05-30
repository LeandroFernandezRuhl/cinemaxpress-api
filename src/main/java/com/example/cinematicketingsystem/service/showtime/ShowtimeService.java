package com.example.cinematicketingsystem.service.showtime;

import com.example.cinematicketingsystem.entity.Showtime;

import java.util.List;

public interface ShowtimeService {
    List<Showtime> findShowtimesByMovieId(Long movieId);
    Showtime findById(Long id);
}
