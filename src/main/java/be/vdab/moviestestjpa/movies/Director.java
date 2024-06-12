package be.vdab.moviestestjpa.movies;

import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "directors")
public class Director {
    @Id
    private long id;
    private String firstname;
    private String lastname;
    @ManyToMany
    @JoinTable(
            name = "moviesdirectors",
            joinColumns = @JoinColumn(name = "directorId"),
            inverseJoinColumns = @JoinColumn(name = "movieId"))
    private Set<Movie> movies = new LinkedHashSet<>();

    public long getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getName() {
        return firstname + " " + lastname;
    }
}
