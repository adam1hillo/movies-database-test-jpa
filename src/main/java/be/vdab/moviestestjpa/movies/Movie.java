package be.vdab.moviestestjpa.movies;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "movies")
public class Movie {
    @Id
    private long id;
    private String name;
    private int year;
    private BigDecimal ranking;


    @ManyToMany(mappedBy = "movies")
    @OrderBy("name")
    private Set<Genre> genres = new LinkedHashSet<>();

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "distributorId")
    private Distributor distributor;
    @ManyToMany(mappedBy = "movies")
    @OrderBy("firstname, lastname")
    private Set<Director> directors = new LinkedHashSet<>();

    @OneToMany(mappedBy = "movie")
    @OrderBy("name")
    private Set<Role> roles = new LinkedHashSet<>();
    @ElementCollection
    @CollectionTable(name = "comments",
    joinColumns = @JoinColumn(name = "movieId"))
    @OrderBy("moment")
    private Set<Comment> comments = new LinkedHashSet<>();
    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getYear() {
        return year;
    }

    public BigDecimal getRanking() {
        return ranking;
    }

    public void setRanking(BigDecimal ranking) {
        this.ranking = ranking;
    }

    public Set<Role> getRoles() {
        return Collections.unmodifiableSet(roles);
    }

    public Set<Genre> getGenres() {
        return Collections.unmodifiableSet(genres);
    }

    public Distributor getDistributor() {
        return distributor;
    }

    public Set<Director> getDirectors() {
        return Collections.unmodifiableSet(directors);
    }

    public Set<Comment> getComments() {
        return Collections.unmodifiableSet(comments);
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof Movie movie && movie.name.equalsIgnoreCase(name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name.toLowerCase());
    }
}
