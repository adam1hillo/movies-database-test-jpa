package be.vdab.moviestestjpa.movies;

import jakarta.persistence.Embeddable;


import java.time.LocalDateTime;
import java.util.Objects;

@Embeddable
public class Comment {
    private String emailAddress;
    private String comment;
    private LocalDateTime moment;

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getComment() {
        return comment;
    }

    public LocalDateTime getMoment() {
        return moment;
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof Comment c && c.moment.equals(moment);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(moment);
    }
}
