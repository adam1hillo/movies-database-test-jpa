package be.vdab.moviestestjpa.movies;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "actors")
public class Actor {
    @Id
    private long id;
    private String firstname;
    private String lastname;
    @Enumerated(EnumType.STRING)
    private Gender gender;


    public long getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public Gender getGender() {
        return gender;
    }
}
