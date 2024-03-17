package com.leandroruhl.cinemaxpress.controller;

import com.leandroruhl.cinemaxpress.model.Movie;
import com.leandroruhl.cinemaxpress.model.Showtime;
import com.leandroruhl.cinemaxpress.model.ShowtimeSeat;
import com.leandroruhl.cinemaxpress.model.Ticket;
import com.leandroruhl.cinemaxpress.service.cinemaroom.CinemaRoomService;
import com.leandroruhl.cinemaxpress.service.movie.MovieService;
import com.leandroruhl.cinemaxpress.service.showtime.ShowtimeService;
import com.leandroruhl.cinemaxpress.service.ticket.TicketService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class SeatBookingControllerTests extends PostgreSQLContainerInitializer {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MovieService movieService;
    @Autowired
    private ShowtimeService showtimeService;
    @Autowired
    private CinemaRoomService cinemaRoomService;
    @Autowired
    private TicketService ticketService;

    @Test
    public void testGetAvailableMovies() throws Exception {
        Movie movie = new Movie();
        movie.setId(23L);
        movie.setTitle("Test Movie");
        movie.setAvailable(true);
        movie.setDurationInMinutes(120L);
        movie.setOverview("Test Overview");
        movieService.saveMovie(movie);

        mockMvc.perform(get("/public/movies"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].title").value("Test Movie"));
    }

    @Test
    public void testGetShowtimesByMovieId() throws Exception {
        cinemaRoomService.createRoom(true, false, 5, 5, 30D);

        showtimeService.saveShowtime(LocalDateTime.parse("2023-07-10T15:56:00"),
                LocalDateTime.parse("2023-08-09T20:21:00"), 23L, 1L);

        mockMvc.perform(get("/public/movies/{id}/showtimes", 23L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].showtimeId").value(1L));
    }


    @Test
    public void testGetAvailableSeats() throws Exception {
        cinemaRoomService.createRoom(true, false, 5, 5, 30D);
        Showtime showtime = showtimeService.saveShowtime(LocalDateTime.parse("2023-09-10T15:56:00"),
                LocalDateTime.parse("2023-10-09T20:21:00"), 23L, 1L);

        mockMvc.perform(get("/public/showtimes/{showtimeId}/seats", showtime.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    public void testPurchaseSeat() throws Exception {
        mockMvc.perform(post("/public/seats/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    public void testRefundSeat() throws Exception {
        cinemaRoomService.createRoom(true, false, 5, 5, 30D);
        Showtime showtime = showtimeService.saveShowtime(LocalDateTime.parse("2025-07-10T15:56:00"),
                LocalDateTime.parse("2025-08-09T20:21:00"), 23L, 1L);
        ShowtimeSeat showtimeSeat = showtime.findSeatById(1L);
        Ticket ticket = ticketService.generateTicket(showtimeSeat.getSeat(), showtime);

        mockMvc.perform(delete("/public/ticket")
                        .param("id", ticket.getId().toString()))
                .andExpect(status().isOk())
                .andExpect(content().string("Ticket successfully refunded"));
    }
}