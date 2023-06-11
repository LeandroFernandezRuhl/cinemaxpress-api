package com.example.cinematicketingsystem.service.showtime;

import com.example.cinematicketingsystem.entity.CinemaRoom;
import com.example.cinematicketingsystem.entity.Movie;
import com.example.cinematicketingsystem.entity.Showtime;
import com.example.cinematicketingsystem.entity.Ticket;

import java.time.LocalDateTime;
import java.util.List;

public interface ShowtimeService {
    void saveShowtime(Movie movie, CinemaRoom room, LocalDateTime startTime, LocalDateTime endTime);
    List<Showtime> findShowtimesByMovieId(Long movieId);
    Showtime findById(Long id);
    Showtime findByTicket(Ticket ticket);
}
