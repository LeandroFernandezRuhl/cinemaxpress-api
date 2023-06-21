package com.example.cinematicketingsystem.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.example.cinematicketingsystem.entity.CinemaRoom;

/**
 * Data Transfer Object (DTO) class representing the update information for a {@link CinemaRoom}.
 * <p>
 * This class encapsulates the update information for a cinema room, including the availability of
 * 3D technology, the availability of surround sound, and the base seat price.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateRoomDTO {
    /**
     * Indicates whether the cinema room has 3D technology.
     */
    @NotNull
    Boolean has3d;
    /**
     * Indicates whether the cinema room has surround sound.
     */
    @NotNull
    Boolean hasSurround;
    /**
     * The base price of a seat in the cinema room.
     */
    @NotNull
    @Positive
    Double price;
}
