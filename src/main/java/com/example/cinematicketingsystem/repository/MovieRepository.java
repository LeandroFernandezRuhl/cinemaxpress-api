package com.example.cinematicketingsystem.repository;

import com.example.cinematicketingsystem.entity.Movie;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MovieRepository extends CrudRepository<Movie, Long> {
    Optional<List<Movie>> findAllMovies();
}
