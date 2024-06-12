package be.vdab.moviestestjpa.movies;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "roles")

public class Role {
    @EmbeddedId
    private RoleId roleId;
    @ManyToOne()
    @MapsId("actorId")
    @JoinColumn(name = "actorId")
    private Actor actor;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("movieId")
    @JoinColumn(name = "movieId")
    private Movie movie;
    private String name;


    public String getName() {
        return name;
    }

    public RoleId getRoleId() {
        return roleId;
    }

    public Actor getActor() {
        return actor;
    }

    public Movie getMovie() {
        return movie;
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof Role rol && rol.name.equalsIgnoreCase(name) && rol.movie.equals(movie);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name.toLowerCase(), movie);
    }
}
