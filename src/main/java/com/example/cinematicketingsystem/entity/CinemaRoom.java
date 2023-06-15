package com.example.cinematicketingsystem.entity;

import com.example.cinematicketingsystem.repository.SeatRepository;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.core.annotation.Order;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "cinema_rooms")
@Order(1)
public class CinemaRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Boolean has3d;
    private Boolean hasSurround;
    private Integer numberOfRows;
    private Integer numberOfColumns;
    private Double baseSeatPrice;
    @OneToMany(mappedBy = "cinemaRoom", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<Showtime> showtimes;
    @OneToMany(mappedBy = "cinemaRoom", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<Seat> seats;

    public CinemaRoom(Boolean has3d, Boolean hasSurround, Integer numberOfRows, Integer numberOfColumns, Double baseSeatPrice) {
        this.has3d = has3d;
        this.hasSurround = hasSurround;
        this.numberOfRows = numberOfRows;
        this.numberOfColumns = numberOfColumns;
        this.baseSeatPrice = baseSeatPrice;
        this.seats = new HashSet<>();
        this.showtimes = new HashSet<>();
    }

    public void initializeSeats(SeatRepository seatRepository) {
        for (int i = 1; i <= numberOfRows; i++) {
            for (int j = 1; j <= numberOfColumns; j++) {
                Seat seat = new Seat(null,i,j,baseSeatPrice,this);
                seats.add(seatRepository.save(seat));
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CinemaRoom that = (CinemaRoom) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
