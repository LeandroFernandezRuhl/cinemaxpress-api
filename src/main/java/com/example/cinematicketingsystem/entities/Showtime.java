package com.example.cinematicketingsystem.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "showtimes")
public class Showtime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime dateTime;
    @ManyToOne
    @JoinColumn(name = "cinemaRoomId") //fetch?
    private CinemaRoom cinemaRoom;
    @OneToMany(mappedBy = "showtime") //fetch and cascade?
    private Set<SeatAvailability> availableSeats;
    @ManyToOne
    @JoinColumn(name = "movieId") //fetch?
    private Movie movie;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Showtime showtime = (Showtime) o;
        return id.equals(showtime.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
