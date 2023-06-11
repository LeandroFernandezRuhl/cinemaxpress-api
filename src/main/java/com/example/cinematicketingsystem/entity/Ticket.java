package com.example.cinematicketingsystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Entity
//@Table(name = "tickets")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "seat_row")
    private int row;
    @Column(name = "seat_column")
    private int column;
    private double price;
    private Long roomNumber;
    private String movieTitle;
    private LocalDateTime showtimeStartTime;
    private LocalDateTime showtimeEndTime;
    private LocalDateTime issueDateTime;

    public Ticket(Seat seat, Showtime showtime) {
        row = seat.getRow();
        column = seat.getColumn();
        price = seat.getPrice();
        roomNumber = showtime.getCinemaRoom().getId();
        movieTitle = showtime.getMovie().getTitle();
        showtimeStartTime = showtime.getStartTime();
        showtimeEndTime = showtime.getEndTime();
        issueDateTime = LocalDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return id.equals(ticket.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", row=" + row +
                ", column=" + column +
                ", price=" + price +
                ", roomNumber=" + roomNumber +
                ", movieTitle='" + movieTitle + '\'' +
                ", showtimeStartTime=" + showtimeStartTime +
                ", showtimeEndTime=" + showtimeEndTime +
                ", issueDateTime=" + issueDateTime +
                '}';
    }
}
