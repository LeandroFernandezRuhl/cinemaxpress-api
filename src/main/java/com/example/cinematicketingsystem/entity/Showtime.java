package com.example.cinematicketingsystem.entity;

import com.example.cinematicketingsystem.repository.ShowtimeSeatRepository;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.core.annotation.Order;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "showtimes")
@Order(4)
public class Showtime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime dateTime;
    @ManyToOne
    @JoinColumn(name = "cinema_room_id")
    private CinemaRoom cinemaRoom;
    @OneToMany(mappedBy = "showtime", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<ShowtimeSeat> seats;
    @ManyToOne
    @JoinColumn(name = "movie_id", foreignKey = @ForeignKey(name = "fk_showtime_movie")) //fetch lazy?
    private Movie movie;

    public void initializeAvailableSeats(ShowtimeSeatRepository showtimeSeatRepository) {
        Set<Seat> roomSeats = cinemaRoom.getSeats();
        for (Seat seat : roomSeats) {
            ShowtimeSeat showtimeSeat = new ShowtimeSeat(null, true, seat, this);
            seats.add(showtimeSeatRepository.save(showtimeSeat));
        }
    }

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

    @Override
    public String toString() {
        return "Showtime{" +
                "id=" + id +
                '}';
    }
}
