package be.vdab.moviestestjpa.movies;

import jakarta.persistence.*;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "genres")
public class Genre {
    @Id
    private long id;
    private String name;
    @ManyToMany
    @JoinTable(
            name = "moviesgenres",
            joinColumns = @JoinColumn(name = "genreId"),
            inverseJoinColumns = @JoinColumn(name = "movieId"))
    private Set<Movie> movies = new LinkedHashSet<>();

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }


    @Override
    public boolean equals(Object object) {
        return object instanceof Genre genre && genre.name.equalsIgnoreCase(name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name.toLowerCase());
    }
}
