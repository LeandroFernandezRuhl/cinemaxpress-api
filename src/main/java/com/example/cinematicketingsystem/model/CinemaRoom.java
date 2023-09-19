package com.example.cinematicketingsystem.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.core.annotation.Order;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * This class represents a room in a cinema. It contains information about a cinema room,
 * such as its availability of 3D technology, surround sound, number of rows, number of columns,
 * base seat price, and associated showtimes and seats.
 *
 * @see Showtime
 * @see Seat
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "cinema_room")
@Order(1)
public class CinemaRoom {
    /**
     * The unique identifier of the cinema room.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * Indicates whether the room has 3D technology.
     */
    private Boolean has3d;
    /**
     * Indicates whether the room has surround sound.
     */
    private Boolean hasSurround;
    /**
     * How many rows does the room have.
     */
    private Integer numberOfRows;
    /**
     * How many columns does the room have.
     */
    private Integer numberOfColumns;
    /**
     * The base price for a seat in the room.
     */
    private Double baseSeatPrice;
    /**
     * The showtimes that take place in this room.
     */
    @OneToMany(mappedBy = "cinemaRoom", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<Showtime> showtimes;
    /**
     * The seats in the room.
     */
    @OneToMany(mappedBy = "cinemaRoom", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<Seat> seats;

    /**
     * Constructs a new cinema room with the specified attributes.
     *
     * @param has3d           whether the room has 3D
     * @param hasSurround     whether the room has surround
     * @param numberOfRows    number of rows in the room
     * @param numberOfColumns number of columns in the room
     * @param baseSeatPrice   base price for a seat in the room
     */
    public CinemaRoom(
            Boolean has3d, Boolean hasSurround, Integer numberOfRows, Integer numberOfColumns, Double baseSeatPrice) {
        this.has3d = has3d;
        this.hasSurround = hasSurround;
        this.numberOfRows = numberOfRows;
        this.numberOfColumns = numberOfColumns;
        this.baseSeatPrice = baseSeatPrice;
        seats = new HashSet<>();
        showtimes = new HashSet<>();
        initializeSeats();
    }

    /**
     * Initializes the seats of this cinema room.
     */
    private void initializeSeats() {
        Set<Seat> roomSeats = new HashSet<>();
        for (int i = 1; i <= numberOfRows; i++) {
            for (int j = 1; j <= numberOfColumns; j++) {
                Seat seat = new Seat(null, i, j, baseSeatPrice, this);
                roomSeats.add(seat);
            }
        }
        setSeats(roomSeats);
    }

    /**
     * Checks if this cinema room is equal to another object.
     *
     * @param o the object to compare to
     * @return {@code true} if the rooms are equal, {@code false} otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CinemaRoom that = (CinemaRoom) o;
        return Objects.equals(id, that.id) && Objects.equals(has3d,
                that.has3d) && Objects.equals(hasSurround, that.hasSurround) && Objects.equals(
                numberOfRows, that.numberOfRows) && Objects.equals(numberOfColumns,
                that.numberOfColumns) && Objects.equals(baseSeatPrice, that.baseSeatPrice);
    }

    /**
     * Calculates the hash code of this cinema room.
     *
     * @return the hash code value of this room
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, has3d, hasSurround, numberOfRows, numberOfColumns, baseSeatPrice);
    }

    /**
     * Generates a string representation of this cinema room.
     *
     * @return a string representation of this room
     */
    @Override
    public String toString() {
        return "CinemaRoom{" +
                "id=" + id +
                ", has3d=" + has3d +
                ", hasSurround=" + hasSurround +
                ", numberOfRows=" + numberOfRows +
                ", numberOfColumns=" + numberOfColumns +
                ", baseSeatPrice=" + baseSeatPrice +
                '}';
    }
}
