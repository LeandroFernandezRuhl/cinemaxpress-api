package com.example.cinematicketingsystem.movieapi;

import org.springframework.stereotype.Component;
import org.springframework.web.util.UriUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

@Component
public class MovieApiClient {
    private static final String API_KEY = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIxMGQ2NGQ3NGU3ZmZjM2I0ODExNjI3MjkxMDA1YjdmMiIsInN1YiI6IjY0NzQ5NzJhYmUyZDQ5MDEzM2E1MWZkMSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.W515ywf6GW51K2kLX1YSme3GbutjH9mq8hlPtm5fCms";

    public String search(String query) throws IOException, InterruptedException {
        String encodedQuery = UriUtils.encodePathSegment(query, "UTF-8");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.themoviedb.org/3/search/movie?query=" + encodedQuery + "&include_adult=false&language=en-US&page=1"))
                .header("accept", "application/json")
                .header("Authorization", "Bearer " + API_KEY)
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();

        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

}
