package com.example.cinematicketingsystem.service.movie;

import com.example.cinematicketingsystem.entity.Movie;
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
    public List<Movie> findAllMovies() {
        log.debug("Finding all movies");
        List<Movie> movies = new ArrayList<>();
        movieRepository.findAll().iterator().forEachRemaining(movies::add);
        if (movies.size() == 0)
            throw new EntityNotFoundException("No movies found");
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
