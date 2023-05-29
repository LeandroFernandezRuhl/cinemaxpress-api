package com.example.cinematicketingsystem.service.showtime;

import com.example.cinematicketingsystem.entity.Showtime;

import java.util.List;

public interface ShowtimeService {
    public List<Showtime> findShowtimesByMovieId(Long movieId);
}
