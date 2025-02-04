package lt.caeli.betterMovies.repository;

import lt.caeli.betterMovies.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> {
  boolean existsByTitle(String title);

  boolean existsByDirector(String director);
}
