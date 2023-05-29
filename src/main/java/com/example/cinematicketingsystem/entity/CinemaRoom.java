package com.example.cinematicketingsystem.entity;

import com.example.cinematicketingsystem.repository.SeatRepository;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.core.annotation.Order;

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
    private String roomNumber;
    private boolean has3d;
    private boolean hasSurround;
    private int numberOfRows;
    private int numberOfColumns;
    @OneToMany(mappedBy = "cinemaRoom", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<Seat> seats;

    public void initializeSeats(SeatRepository seatRepository, Double seatPrice) {
        for (int i = 1; i <= numberOfRows; i++) {
            for (int j = 1; j <= numberOfColumns; j++) {
                Seat seat = new Seat(null,i,j,seatPrice,this);
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
