package com.example.cinematicketingsystem.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShowtimeInfoDTO {
    private Long showtimeId;
    private Long roomId;
    private Boolean roomHas3d;
    private Boolean roomHasSurround;
    private Long movieId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
