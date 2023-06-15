package com.example.cinematicketingsystem.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class CreateRoomDTO {
    @NotNull
    Boolean has3d;
    @NotNull
    Boolean hasSurround;
    @NotNull @Positive
    Integer rows;
    @NotNull @Positive
    Integer columns;
    @NotNull @Positive
    Double price;
}
