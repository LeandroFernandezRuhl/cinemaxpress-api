package com.example.cinematicketingsystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * This class represents a ticket for a specific seat of a showtime. It contains information about a ticket,
 * such as its unique identifier, seat location, price, room number, movie title,
 * showtime start and end times, and issue date and time.
 * @see Showtime
 * @see Movie
 * @see CinemaRoom
 * @see Seat
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tickets")
public class Ticket {
    /**
     * The unique identifier of the ticket.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    /**
     * The row number of the seat associated with the ticket.
     */
    @Column(name = "seat_row")
    private int row;
    /**
     * The column number of the seat associated with the ticket.
     */
    @Column(name = "seat_column")
    private int column;
    /**
     * The price of the ticket.
     */
    private Double price;
    /**
     * The day and hour when the showtime starts.
     */
    private LocalDateTime showtimeStartTime;
    /**
     * The day and hour when the showtime ends.
     */
    private LocalDateTime showtimeEndTime;
    /**
     * The number of the cinema room where the showtime takes place.
     */
    private Long roomNumber;
    /**
     * The title of the movie screened during the showtime.
     */
    private String movieTitle;
    /**
     * The ID of the movie screened during the showtime.
     */
    private Long movieId;
    /**
     * The day and hour when the ticket was issued.
     */
    private LocalDateTime issueDateTime;

    /**
     * Constructs a new ticket with the given seat and showtime.
     *
     * @param seat the seat associated with the ticket
     * @param showtime the showtime associated with the ticket
     */
    public Ticket(Seat seat, Showtime showtime) {
        row = seat.getRow();
        column = seat.getColumn();
        price = seat.getPrice();
        roomNumber = showtime.getCinemaRoom().getId();
        movieTitle = showtime.getMovie().getTitle();
        movieId = showtime.getMovie().getId();
        showtimeStartTime = showtime.getStartTime();
        showtimeEndTime = showtime.getEndTime();
        issueDateTime = LocalDateTime.now();
    }

    /**
     * Checks if this ticket is equal to another object.
     * Two tickets are considered equal if their IDs are equal.
     *
     * @param o the object to compare to
     * @return {@code true} if the tickets are equal, {@code false} otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return id.equals(ticket.id);
    }

    /**
     * Calculates the hash code of this ticket.
     *
     * @return the hash code of this ticket
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    /**
     * Generates a string representation of this ticket.
     *
     * @return the string representation of this ticket
     */
    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", row=" + row +
                ", column=" + column +
                ", price=" + price +
                ", showtimeStartTime=" + showtimeStartTime +
                ", showtimeEndTime=" + showtimeEndTime +
                ", roomNumber=" + roomNumber +
                ", movieTitle='" + movieTitle + '\'' +
                ", movieId=" + movieId +
                ", issueDateTime=" + issueDateTime +
                '}';
    }
}
