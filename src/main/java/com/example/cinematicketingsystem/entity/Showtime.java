package com.example.cinematicketingsystem.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.core.annotation.Order;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * This class represents a showtime for a movie in a cinema room.
 * It contains information about the start & end times, cinema room, seats, and associated movie of a showtime.
 * @see ShowtimeSeat
 * @see CinemaRoom
 * @see Movie
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "showtimes")
@Order(4)
public class Showtime {
    /**
     * The unique identifier of the showtime.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * The day and hour when the showtime starts.
     */
    private LocalDateTime startTime;
    /**
     * The day and hour when the showtime ends.
     */
    private LocalDateTime endTime;
    /**
     * The cinema room where the showtime takes place.
     */
    @ManyToOne
    @JoinColumn(name = "cinema_room_id")
    private CinemaRoom cinemaRoom;
    /**
     * The seats associated with the showtime.
     */
    @OneToMany(mappedBy = "showtime", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<ShowtimeSeat> seats;
    /**
     * The movie screened during the showtime.
     */
    @ManyToOne
    @JoinColumn(name = "movie_id", foreignKey = @ForeignKey(name = "fk_showtime_movie"))
    private Movie movie;

    /**
     * Constructs a new showtime with the given start time, end time, cinema room, and movie.
     *
     * @param startTime the start time of the showtime
     * @param endTime the end time of the showtime
     * @param cinemaRoom the cinema room where the showtime takes place
     * @param movie the movie associated with the showtime
     */
    public Showtime(LocalDateTime startTime, LocalDateTime endTime, CinemaRoom cinemaRoom, Movie movie) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.cinemaRoom = cinemaRoom;
        this.movie = movie;
        this.seats = new HashSet<>();
        initializeSeats();
    }

    /**
     * Initializes the seats of this showtime using the seats of its cinema room.
     */
    private void initializeSeats() {
        Set<Seat> roomSeats = cinemaRoom.getSeats();
        Set<ShowtimeSeat> showtimeSeats = new HashSet<>();
        for (Seat seat : roomSeats) {
            ShowtimeSeat showtimeSeat = new ShowtimeSeat(null, true, seat, this);
            showtimeSeats.add(showtimeSeat);
        }
        setSeats(showtimeSeats);
    }

    /**
     * Checks if this showtime is equal to another object.
     * Two showtimes are considered equal if their start times, end times, cinema rooms and movies are equal.
     *
     * @param o the object to compare to.
     * @return {@code true} if the showtimes are equal, {@code false} otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Showtime showtime = (Showtime) o;
        return Objects.equals(startTime, showtime.startTime) && Objects.equals(endTime,
                showtime.endTime) && Objects.equals(cinemaRoom, showtime.cinemaRoom) && Objects.equals(
                movie, showtime.movie);
    }

    /**
     * Calculates the hash code of this showtime.
     *
     * @return the hash code value of this showtime
     */
    @Override
    public int hashCode() {
        return Objects.hash(startTime, endTime, cinemaRoom, movie);
    }

    /**
     * Generates a string representation of this showtime.
     *
     * @return a string representation of this showtime
     */
    @Override
    public String toString() {
        return "Showtime{" +
                "id=" + id +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", cinemaRoom=" + cinemaRoom +
                ", movie=" + movie +
                '}';
    }
}
