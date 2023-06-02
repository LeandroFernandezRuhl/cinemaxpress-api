package com.example.cinematicketingsystem.repository;

import com.example.cinematicketingsystem.entity.Ticket;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TicketRepository extends CrudRepository<Ticket, String> {
    Optional<Ticket> findById(UUID uuid);
}
