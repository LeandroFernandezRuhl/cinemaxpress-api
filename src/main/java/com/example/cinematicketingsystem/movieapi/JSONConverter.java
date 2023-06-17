package com.example.cinematicketingsystem.movieapi;

import com.example.cinematicketingsystem.entity.Movie;
import com.example.cinematicketingsystem.exception.MovieApiException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JSONConverter {

    public List<Movie> JSONToMovieList(String json) throws JSONException {
        JSONObject apiResponse = new JSONObject(json);
        try {
            JSONArray results = apiResponse.getJSONArray("results");
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

    public String MovieListToJSON(List<Movie> movieList) throws JSONException {
        return new JSONArray(movieList).toString();
    }
}
