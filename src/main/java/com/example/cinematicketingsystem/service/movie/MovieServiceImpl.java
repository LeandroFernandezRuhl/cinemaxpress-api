package com.example.cinematicketingsystem.service.movie;

import com.example.cinematicketingsystem.entity.*;
import com.example.cinematicketingsystem.exception.EntityAlreadyExistsException;
import com.example.cinematicketingsystem.exception.MovieApiException;
import com.example.cinematicketingsystem.movieapi.JSONConverter;
import com.example.cinematicketingsystem.movieapi.MovieApiClient;
import com.example.cinematicketingsystem.repository.MovieRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@AllArgsConstructor
@Service
public class MovieServiceImpl implements MovieService {

    private MovieRepository movieRepository;
    private MovieApiClient movieApiClient;
    private JSONConverter jsonConverter;

    @Override
    public void saveMovie(Movie movie) {
        log.debug("Generating movie with ID = {}", movie.getId());
        if (movieRepository.existsById(movie.getId())) {
            throw new EntityAlreadyExistsException(Movie.class, movie.getId().toString());
        }
        movieRepository.save(movie);
    }

    @Override
    public Movie changeAvailability(Long id) {
        log.debug("Changing availability of movie with ID = {}", id);
        Movie movie = findById(id);
        movie.setAvailable(!movie.getAvailable());
        return movieRepository.save(movie);
    }

    //throws dataIntegrityViolationException
    @Override
    public void deleteMovie(Long id) {
        log.debug("Deleting movie with ID = {}", id);
        Movie movie = findById(id);
        movieRepository.delete(movie);
    }

    @Override
    public Movie findById(Long id) {
        log.debug("Finding movie with ID = {}", id);
        return movieRepository.findById(id)
                .orElseThrow(() -> new com.example.cinematicketingsystem.exception.EntityNotFoundException(Movie.class, "id", id.toString()));
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

    @Override
    public List<Movie> findAllAvailableMovies() {
        log.debug("Finding all available movies");
        List<Movie> movies = movieRepository.findAllByAvailableIsTrue();
        if (movies.size() == 0)
            throw new EntityNotFoundException("No available movies found");
        return movies;
    }


    @Override
    public String searchInMovieApi(String title) {
        String jsonResponse = null;
        try {
            jsonResponse = movieApiClient.search(title);
        } catch (IOException | InterruptedException e) {
            throw new MovieApiException(e);
        }
        List<Movie> movieList = jsonConverter.JSONToMovieList(jsonResponse);
        if (movieList.isEmpty()) {
            throw new MovieApiException(title);
        }
        return jsonConverter.MovieListToJSON(movieList);
    }
}
