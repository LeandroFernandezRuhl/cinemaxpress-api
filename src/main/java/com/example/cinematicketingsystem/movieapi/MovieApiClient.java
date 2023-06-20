package com.example.cinematicketingsystem.movieapi;

import org.springframework.stereotype.Component;
import org.springframework.web.util.UriUtils;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * Client for making API requests to retrieve movie data.
 * <p>
 * The {@code MovieApiClient} class provides a method for searching movies based on a query string. It sends an HTTP GET
 * request to an external movie API and retrieves the response as a JSON string.
 */
@Component
public class MovieApiClient {
    private static final String API_KEY = "";

    /**
     * Searches movies based on the given query.
     *
     * @param query the search query
     * @return the JSON response as a string
     * @throws IOException          if an I/O error occurs while sending the request or receiving the response
     * @throws InterruptedException if the operation is interrupted while waiting for the response
     */
    public String search(String query) throws IOException, InterruptedException {
        String encodedQuery = UriUtils.encodePathSegment(query, "UTF-8");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(
                        "https://api.themoviedb.org/3/search/movie?query=" + encodedQuery + "&include_adult=false&language=en-US&page=1"))
                .header("accept", "application/json")
                .header("Authorization", "Bearer " + API_KEY)
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

}
