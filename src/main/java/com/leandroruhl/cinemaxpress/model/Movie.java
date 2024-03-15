package com.leandroruhl.cinemaxpress.model;

import com.leandroruhl.cinemaxpress.exception.MovieApiException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.core.annotation.Order;

import java.util.Objects;

/**
 * This class represents a movie. It contains information about a movie, such as its availability,
 * title, overview, poster path, and duration in minutes.
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "movie")
@Order(3)
public class Movie {
    /**
     * The unique identifier of the movie.
     */
    @Id
    @NotNull
    private Long id;
    /**
     * Indicates whether the movie is available.
     */
    @NotNull
    private Boolean available;
    /**
     * The title of the movie.
     */
    @NotBlank
    private String title;
    /**
     * A short description of what the movie is about.
     */
    @Column(columnDefinition = "TEXT")
    @NotBlank
    private String overview;
    /**
     * The path to the poster of the movie.
     */
    private String posterPath;
    /**
     * The duration of the movie in minutes
     */
    @NotNull
    private Long durationInMinutes;

    /**
     * Constructs a new movie with the specified attributes.
     *
     * @param id         the unique identifier of the movie
     * @param title      the title of the movie
     * @param overview   the overview of the movie
     * @param posterPath the path of the movie poster
     */
    public Movie(@NotNull Long id, String title, String overview, String posterPath) {
        this.id = id;
        this.title = title;
        this.overview = overview;
        this.posterPath = "https://image.tmdb.org/t/p/original" + posterPath;
    }

    /**
     * Generates a {@link JSONObject} of this movie.
     *
     * @return the {@link JSONObject} of this movie
     */
    public JSONObject toJSON() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", id);
            jsonObject.put("available", available);
            jsonObject.put("title", title);
            jsonObject.put("overview", overview);
            jsonObject.put("posterPath", posterPath);
            jsonObject.put("durationInMinutes", durationInMinutes);
        } catch (JSONException e) {
            throw new MovieApiException(
                    "An error occurred while making the JSONObject of the movie: " + e.getMessage());
        }
        return jsonObject;
    }

    /**
     * Checks if this movie is equal to another object.
     * Two movies are considered equal if their IDs are equal.
     *
     * @param o the object to compare to
     * @return {@code true} if the movies are equal, {@code false} otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return Objects.equals(id, movie.id);
    }

    /**
     * Calculates the hash code of this movie.
     *
     * @return the hash code value of this movie
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    /**
     * Generates a string representation of this movie.
     *
     * @return the string representation of this movie
     */
    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", available=" + available +
                ", title='" + title + '\'' +
                ", overview='" + overview + '\'' +
                ", posterPath='" + posterPath + '\'' +
                ", durationInMinutes=" + durationInMinutes +
                '}';
    }
}
