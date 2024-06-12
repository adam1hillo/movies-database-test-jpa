package be.vdab.moviestestjpa.movies;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class MovieNietGevondenException extends RuntimeException{
    MovieNietGevondenException() {
        super("Movie niet gevonden");
    }
}
