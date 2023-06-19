package com.example.cinematicketingsystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.core.annotation.Order;

import java.util.Objects;

/**
 * This class represents the availability of a seat for a specific showtime. It contains information
 * such as its associated seat, associated showtime and its availability status.
 * @see Showtime
 * @see Seat
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "available_seats")
@Order(5)
public class ShowtimeSeat {
    /**
     * The unique identifier of the showtime seat.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * Indicates whether the showtime seat is available.
     */
    private boolean available;
    /**
     * The seat associated with the showtime seat.
     */
    @ManyToOne
    @JoinColumn(name = "seat_id")
    private Seat seat;
    /**
     * The showtime associated with the showtime seat.
     */
    @ManyToOne
    @JoinColumn(name = "showtime_id")
    private Showtime showtime;

    /**
     * Checks if this showtime seat is equal to another object.
     * Two showtime seats are considered equal if their associated seat and associated showtime are equal.
     *
     * @param o the object to compare to
     * @return {@code true} if the showtime seats are equal, {@code false} otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShowtimeSeat that = (ShowtimeSeat) o;
        return Objects.equals(seat, that.seat) && Objects.equals(showtime, that.showtime);
    }

    /**
     * Calculates the hash code of this showtime seat.
     *
     * @return the hash code of this showtime seat
     */
    @Override
    public int hashCode() {
        return Objects.hash(seat, showtime);
    }

    /**
     * Generates a string representation of this showtime seat.
     *
     * @return the string representation of this showtime seat
     */
    @Override
    public String toString() {
        return "ShowtimeSeat{" +
                "id=" + id +
                ", available=" + available +
                ", seat=" + seat +
                ", showtime=" + showtime +
                '}';
    }
}
