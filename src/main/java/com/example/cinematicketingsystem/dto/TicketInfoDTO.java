package com.example.cinematicketingsystem.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.example.cinematicketingsystem.entity.Ticket;

/**
 * Data Transfer Object (DTO) class representing information about ticket statistics.
 * <p>
 * This class encapsulates information about {@link Ticket} statistics, including the number of tickets sold
 * and the total revenue generated from ticket sales.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TicketInfoDTO {
    /**
     * The number of tickets sold.
     */
    Integer ticketsSold;
    /**
     * The total revenue generated from ticket sales.
     */
    Double revenue;
}
