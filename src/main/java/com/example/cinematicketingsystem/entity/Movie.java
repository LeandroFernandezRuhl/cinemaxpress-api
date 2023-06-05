package com.example.cinematicketingsystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.core.annotation.Order;

import java.util.Objects;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "movies")
@Order(3)
public class Movie {
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String overview;
    private String posterPath;
    private Long duration;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return id.equals(movie.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
