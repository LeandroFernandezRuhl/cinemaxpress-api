package com.leandroruhl.cinemaxpress.dto;

import com.leandroruhl.cinemaxpress.model.Seat;
import com.leandroruhl.cinemaxpress.model.ShowtimeSeat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


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
    Long id;
    /**
     * The row number of the seat.
     */
    Integer row;
    /**
     * The column number of the seat.
     */
    Integer column;
    /**
     * The price of the seat.
     */
    Double price;
}
