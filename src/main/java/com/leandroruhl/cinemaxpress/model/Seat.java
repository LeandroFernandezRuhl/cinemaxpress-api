package com.leandroruhl.cinemaxpress.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.core.annotation.Order;

import java.util.Objects;

/**
 * This class represents a seat in a cinema room. It contains information about a seat,
 * such as its row, column, price, and the associated cinema room.
 *
 * @see CinemaRoom
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "seat")
@Order(2)
public class Seat {
    /**
     * The unique identifier of the seat.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * The row number of the seat.
     */
    @Column(name = "seat_row")
    private Integer row;
    /**
     * The column number of the seat.
     */
    @Column(name = "seat_column")
    private Integer column;
    /**
     * The price of the seat.
     */
    private Double price;
    /**
     * The cinema room associated with the seat.
     */
    @ManyToOne
    @JoinColumn(name = "cinema_room_id")
    private CinemaRoom cinemaRoom;

    /**
     * Checks if this seat is equal to another object.
     * Two seats are considered equal if their row, column, price and associated cinema room are equal.
     *
     * @param o the object to compare to
     * @return {@code true} if the seats are equal, {@code false} otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Seat seat = (Seat) o;
        return Objects.equals(row, seat.row) && Objects.equals(column, seat.column) && Double.compare(seat.price,
                price) == 0 && Objects.equals(cinemaRoom, seat.cinemaRoom);
    }

    /**
     * Calculates the hash code of this seat.
     *
     * @return the hash code of this seat
     */
    @Override
    public int hashCode() {
        return Objects.hash(row, column, price, cinemaRoom);
    }

    /**
     * Generates a string representation of this seat.
     *
     * @return the string representation of this seat
     */
    @Override
    public String toString() {
        return "Seat{" +
                "id=" + id +
                ", row=" + row +
                ", column=" + column +
                ", price=" + price +
                ", cinemaRoom=" + cinemaRoom +
                '}';
    }
}
