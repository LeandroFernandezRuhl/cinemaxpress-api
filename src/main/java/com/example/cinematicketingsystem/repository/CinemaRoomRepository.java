package com.example.cinematicketingsystem.repository;

import com.example.cinematicketingsystem.entity.CinemaRoom;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CinemaRoomRepository extends CrudRepository<CinemaRoom, Long> {
}
