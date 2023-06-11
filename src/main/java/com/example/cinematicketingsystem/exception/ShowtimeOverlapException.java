package com.example.cinematicketingsystem.exception;

import com.example.cinematicketingsystem.apierror.OverlappingError;
import com.example.cinematicketingsystem.entity.Showtime;

import java.util.ArrayList;
import java.util.List;

public class ShowtimeOverlapException extends RuntimeException {
    private List<OverlappingError> overlappingErrors;

    public ShowtimeOverlapException(String message, List<Showtime> overlappingShowtimes) {
        super(message);
        overlappingErrors = new ArrayList<>();
        initializeOverlappingErrors(overlappingShowtimes);
    }

   private void initializeOverlappingErrors(List<Showtime> overlappingShowtimes) {
        for (Showtime showtime : overlappingShowtimes) {
        overlappingErrors.add(new OverlappingError(showtime));
        }
   }

   public List<OverlappingError> getOverlappingErrors() {
        return overlappingErrors;
   }
}