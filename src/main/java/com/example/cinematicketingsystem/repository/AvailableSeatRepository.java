package com.example.cinematicketingsystem.repository;

import com.example.cinematicketingsystem.entity.AvailableSeat;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvailableSeatRepository extends CrudRepository<AvailableSeat, Long> {
}
