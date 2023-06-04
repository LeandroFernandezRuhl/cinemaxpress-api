package com.example.cinematicketingsystem.service.movie;

import com.example.cinematicketingsystem.entity.Movie;
import com.example.cinematicketingsystem.repository.MovieRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class MovieServiceImpl implements MovieService {

    MovieRepository movieRepository;

    public MovieServiceImpl(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public List<Movie> findAllMovies() {
        log.debug("Finding all movies");
        List<Movie> movies = new ArrayList<>();
        movieRepository.findAll().iterator().forEachRemaining(movies::add);
        if (movies.size() == 0)
            throw new EntityNotFoundException("No movies found");
        return movies;
    }
}
