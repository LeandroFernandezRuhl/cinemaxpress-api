package com.example.cinematicketingsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.example.cinematicketingsystem.entity.Seat;
import com.example.cinematicketingsystem.entity.ShowtimeSeat;


/**
 * Data Transfer Object (DTO) class representing seat information.
 * <p>
 * This class encapsulates the information about a {@link ShowtimeSeat}  including its unique identifier,
 * row number, column number, and the price associated with the seat.
 *
 * @see Seat
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SeatInfoDTO {
    /**
     * The ID of the showtime seat.
     */
    long id;
    /**
     * The row number of the seat.
     */
    int row;
    /**
     * The column number of the seat.
     */
    int column;
    /**
     * The price of the seat.
     */
    double price;
}
