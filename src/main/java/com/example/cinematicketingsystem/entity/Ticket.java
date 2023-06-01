package com.example.cinematicketingsystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.core.annotation.Order;

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
    private String roomNumber;
    private String movieTitle;
    private LocalDateTime showtimeDateTime;
    private LocalDateTime issueDateTime;

    public Ticket(Seat seat, Showtime showtime) {
        row = seat.getRow();
        column = seat.getColumn();
        price = seat.getPrice();
        roomNumber = showtime.getCinemaRoom().getRoomNumber();
        movieTitle = showtime.getMovie().getTitle();
        showtimeDateTime = showtime.getDateTime();
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
}
