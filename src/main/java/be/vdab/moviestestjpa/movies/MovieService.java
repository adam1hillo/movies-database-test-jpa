package be.vdab.moviestestjpa.movies;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;


@Service
@Transactional(readOnly = true)
public class MovieService {
    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }
    Optional<Movie> findById(long id) {
        return movieRepository.findById(id);
    }
    List<Integer> findYears() {
        return movieRepository.findYears();
    }
    List<Movie> findByYear(int year) {
        return movieRepository.findByYearOrderByName(year);
    }
    List<Comment> findComments(long id) {
        return movieRepository.findComments(id);
    }
    @Transactional
    void changeRanking(long id, BigDecimal ranking) {
        movieRepository.findAndLockById(id)
                .orElseThrow(MovieNietGevondenException::new)
                .setRanking(ranking);
    }

}
