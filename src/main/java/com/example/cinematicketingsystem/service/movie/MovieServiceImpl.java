package com.example.cinematicketingsystem.service.movie;

import com.example.cinematicketingsystem.entity.Movie;
import com.example.cinematicketingsystem.repository.MovieRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class MovieServiceImpl implements MovieService {

    MovieRepository movieRepository;

    public MovieServiceImpl(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }



    public List<Movie> findAllMovies() {
        log.info("Finding all movies");
        Optional<List<Movie>> movies = movieRepository.findAllMovies();

        if (movies.isEmpty()) {
            String errorMessage = "No movies found in the database";
            log.error(errorMessage);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, errorMessage);
        }

        return movies.get();
    }

    //Delete, Save/Update, ?
}
