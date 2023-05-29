package com.example.cinematicketingsystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.core.annotation.Order;

import java.util.Objects;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "seats")
@Order(2)
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "seat_row")
    private int row;
    @Column(name = "seat_column")
    private int column;
    private Double price;
    @ManyToOne
    @JoinColumn(name = "cinema_room_id") //fetch?
    private CinemaRoom cinemaRoom;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Seat seat = (Seat) o;
        return id.equals(seat.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
