package com.example.cinematicketingsystem.movieapi;

import com.example.cinematicketingsystem.entity.Movie;
import com.example.cinematicketingsystem.exception.MovieApiException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for converting JSON data to {@link Movie} objects and vice versa.
 * <p>
 * The {@code MovieJSONConverter} class provides methods for converting JSON data to a list of movie objects and
 * converting a list of movie objects to JSON data. It is used to facilitate the conversion between JSON representations
 * and the movie object model.
 * @see JSONObject
 * @see JSONArray
 */
@Component
public class MovieJSONConverter {

    /**
     * Converts a JSON string to a list of movie objects.
     *
     * @param json the JSON string to be converted
     * @return a list of movie objects parsed from the JSON string
     * @throws MovieApiException if the JSON given by the external API is invalid or malformed
     */
    public List<Movie> JSONToMovieList(String json) throws JSONException {
        JSONObject movieApiResponse = new JSONObject(json);
        try {
            JSONArray results = movieApiResponse.getJSONArray("results");
            List<Movie> movieList = new ArrayList<>();
            for (int i = 0; i < results.length(); i++) {
                JSONObject movie = results.getJSONObject(i);
                Long id = movie.getLong("id");
                String title = movie.getString("title");
                String posterPath = movie.isNull("poster_path") ? null : movie.getString("poster_path");
                String overview = movie.getString("overview");
                movieList.add(new Movie(id, title, overview, posterPath));
            }
            return movieList;
        } catch (JSONException e) {
            throw new MovieApiException(e);
        }
    }

    /**
     * Converts a list of movie objects to a JSON string.
     *
     * @param movieList the list of movie objects to be converted
     * @return a JSON string representation of the list of movie objects
     * @throws JSONException if there is an error while creating the JSONObjects of the movies
     */
    public String MovieListToJSON(List<Movie> movieList) throws JSONException {
        JSONArray jsonArray = new JSONArray();
        for (Movie movie : movieList) {
            jsonArray.put(movie.toJSON());
        }
        return jsonArray.toString();
    }
}
