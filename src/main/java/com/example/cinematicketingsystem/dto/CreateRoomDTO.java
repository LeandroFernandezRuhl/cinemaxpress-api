package com.example.cinematicketingsystem.dto;

import com.example.cinematicketingsystem.model.CinemaRoom;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data Transfer Object (DTO) class for creating a {@link CinemaRoom}.
 * <p>
 * This class encapsulates the data required to create a new cinema room. It is used for transferring
 * data between the client and the server. The fields represent the attributes of a cinema room,
 * such as whether it has 3D capability, surround sound, the number of rows and columns of seats,
 * and the base seat price.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateRoomDTO {
    /**
     * Indicates whether the cinema room has 3D capability.
     */
    @NotNull
    Boolean has3d;
    /**
     * Indicates whether the cinema room has surround sound.
     */
    @NotNull
    Boolean hasSurround;
    /**
     * The number of rows in the cinema room.
     */
    @NotNull
    @Positive
    Integer rows;
    /**
     * The number of columns in the cinema room.
     */
    @NotNull
    @Positive
    Integer columns;
    /**
     * The base price of a seat in the cinema room.
     */
    @NotNull
    @Positive
    Double price;
}
