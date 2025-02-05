package lt.caeli.evenBetterMovies.repository;

import lt.caeli.evenBetterMovies.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> {
  boolean existsByTitle(String title);

  boolean existsByDirector(String director);

  boolean existsByTitleAndDirector(String title, String director);

}
