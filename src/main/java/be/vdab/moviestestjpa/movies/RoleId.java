package be.vdab.moviestestjpa.movies;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class RoleId implements Serializable {
    @Column(name = "actorId")
    private long actorId;
    @Column(name = "movieId")
    private long movieId;

    public long getActorId() {
        return actorId;
    }

    public long getMovieId() {
        return movieId;
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof RoleId rolId && rolId.actorId == actorId && rolId.movieId == movieId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(actorId, movieId);
    }
}
