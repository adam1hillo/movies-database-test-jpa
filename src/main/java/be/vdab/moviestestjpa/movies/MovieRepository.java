package be.vdab.moviestestjpa.movies;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    @EntityGraph(attributePaths = {"genres", "directors"})
    Optional<Movie> findById(long id);
    @Query("select distinct m.year as year from Movie m order by m.year")
    List<Integer> findYears();

    @EntityGraph(attributePaths = "distributor")
    List<Movie> findByYearOrderByName(int year);
    @Query("select m.comments from Movie m where m.id=:id")
    List<Comment> findComments(long id);

    @Lock(LockModeType.PESSIMISTIC_READ)
    @Query("select m from Movie m where m.id=:id")
    Optional<Movie> findAndLockById(long id);


}
