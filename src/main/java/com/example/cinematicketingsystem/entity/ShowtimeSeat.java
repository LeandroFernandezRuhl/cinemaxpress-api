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
@Table(name = "available_seats")
@Order(5)
public class ShowtimeSeat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private boolean available;
    @ManyToOne
    @JoinColumn(name = "seat_id") //fetch?
    private Seat seat;
    @ManyToOne
    @JoinColumn(name = "showtime_id") //fetch?
     private Showtime showtime;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShowtimeSeat that = (ShowtimeSeat) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "ShowtimeSeat{" +
                "id=" + id +
                ", available=" + available +
                ", seat=" + seat +
                '}';
    }
}
