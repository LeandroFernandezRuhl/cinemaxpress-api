package com.example.cinematicketingsystem.service.showtimeSeat;

import com.example.cinematicketingsystem.dto.SeatInfoDTO;
import com.example.cinematicketingsystem.entity.Showtime;
import com.example.cinematicketingsystem.entity.ShowtimeSeat;
import com.example.cinematicketingsystem.entity.Ticket;

import java.util.List;

/**
 * Service interface for managing showtime seats.
 * <p>
 * This interface defines methods for finding available seats, purchasing seats, and refunding seats associated with
 * showtimes. Implementations of this interface handle the business logic associated with showtime seats.
 *
 * @see ShowtimeSeat
 */
public interface ShowtimeSeatService {
    /**
     * Finds available seats for the specified showtime.
     *
     * @param showtimeId the ID of the showtime to find available seats for
     * @return a list of {@link SeatInfoDTO} objects representing the available seats
     */
    public List<SeatInfoDTO> findAvailableSeats(Long showtimeId);

    /**
     * Purchases the seat with the specified ID.
     *
     * @param seatId the ID of the seat to purchase
     * @return the ShowtimeSeat object representing the purchased seat
     */
    public ShowtimeSeat purchaseSeat(Long seatId);

    /**
     * Refunds the seat associated with the specified showtime and ticket.
     *
     * @param showtime the showtime associated with the seat
     * @param ticket   the ticket associated with the seat
     */
    void refundSeat(Showtime showtime, Ticket ticket);
}
