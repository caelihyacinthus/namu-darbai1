package lt.caeli.muchEvenBetterMovie.repository;

import lt.caeli.muchEvenBetterMovie.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {
  boolean existsByTitle(String title);

  boolean existsByDirector(String director);

  boolean existsByTitleAndDirector(String title, String director);

  List<Movie> findAllByTitleContaining(String title);

}
