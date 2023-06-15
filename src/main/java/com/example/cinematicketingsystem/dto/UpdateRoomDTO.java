package com.example.cinematicketingsystem.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class UpdateRoomDTO {
    @NotNull
    Long id;
    @NotNull
    Boolean has3d;
    @NotNull
    Boolean hasSurround;
    @NotNull @Positive
    Double price;
}
