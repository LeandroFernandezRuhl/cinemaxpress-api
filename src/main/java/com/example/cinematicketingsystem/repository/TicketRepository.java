package com.example.cinematicketingsystem.repository;

import com.example.cinematicketingsystem.entity.Ticket;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TicketRepository extends CrudRepository<Ticket, String> {
    List<Ticket> deleteAllByShowtimeStartTime(LocalDateTime startTime);
    Optional<Ticket> findById(UUID uuid);
}
