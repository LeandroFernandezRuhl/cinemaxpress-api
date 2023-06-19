package com.example.cinematicketingsystem.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class TicketInfoDTO {
    Integer ticketsSold;
    Double revenue;
}
