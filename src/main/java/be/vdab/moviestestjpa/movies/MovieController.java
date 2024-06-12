package be.vdab.moviestestjpa.movies;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Stream;

@RestController
@RequestMapping("movies")
public class MovieController {
    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("{id}")
    MovieDetails findById(@PathVariable long id) {
        return movieService.findById(id)
                .map(MovieDetails::new)
                .orElseThrow(MovieNietGevondenException::new);
    }

    @GetMapping("years")
    List<Integer> findYears() {
        return movieService.findYears();
    }

    @GetMapping(params = "year")
    Stream<MovieWithDistributor> findByYear(int year) {
        return movieService.findByYear(year)
                .stream()
                .map(MovieWithDistributor::new);
    }

    /*@GetMapping("{id}/comments")
    Set<Comment> findComments(@PathVariable long id) {
        return movieService.findById(id)
                .map(Movie::getComments)
                .orElseThrow(MovieNietGevondenException::new);
    }*/
    @GetMapping("{id}/comments")
    List<Comment> findComments(@PathVariable long id) {
        return movieService.findComments(id);
    }

    @PatchMapping("{id}/ranking")
    void changeRanking(@PathVariable long id, @RequestBody @NotNull @Min(0) @Max(10) BigDecimal ranking) {
        movieService.changeRanking(id, ranking);
    }

    @GetMapping("{id}/roles")
    Stream<RoleAndActor> findRolesFromAMovie(@PathVariable long id) {
        return movieService.findById(id)
                .orElseThrow(MovieNietGevondenException::new)
                .getRoles()
                .stream()
                .map(RoleAndActor::new);
    }
   /* @GetMapping("{id}/genres")
    Stream<String> findGenresFromMovie(@PathVariable long id) {
        return movieService.findById(id)
                .orElseThrow(MovieNietGevondenException::new)
                .getGenres()
                .stream()
                .map(Genre::getName);
    }
    @GetMapping("{id}/directors")
    Stream<String> findDirectorsFromMovie(@PathVariable long id) {
        return movieService.findById(id)
                .orElseThrow(MovieNietGevondenException::new)
                .getDirectors()
                .stream()
                .map(Director::getName);
    }*/
    record MovieWithDistributor(long id, String name, BigDecimal ranking, String distributor) {
        MovieWithDistributor(Movie movie) {
            this(movie.getId(), movie.getName(), movie.getRanking(), movie.getDistributor().getName());
        }
    }

    record RoleAndActor(String roleName, String actorFirstname, String actorLastname, Gender gender) {
        RoleAndActor(Role role) {
            this(role.getName(), role.getActor().getFirstname(), role.getActor().getLastname(), role.getActor().getGender());
        }
    }
    record MovieDetails(long id, String name, int year, BigDecimal ranking, Stream<String> genres, Stream<String> directors) {
        MovieDetails(Movie movie) {
            this(movie.getId(), movie.getName(), movie.getYear(), movie.getRanking(),
                    movie.getGenres().stream().map(Genre::getName), movie.getDirectors().stream().map(Director::getName));
        }
    }
}
