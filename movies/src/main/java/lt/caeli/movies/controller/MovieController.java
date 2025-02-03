package lt.caeli.movies.controller;

import lt.caeli.movies.model.Movie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class MovieController {
  private List<Movie> movies = new ArrayList<>(List.of(new Movie("hell", "pupupu"), new Movie("heaven", "oioioi")));

  @GetMapping("/movies")
  public ResponseEntity<List<Movie>> getMovies() {
    return ResponseEntity.ok(movies);
  }

  @GetMapping("/movies/{index}")
  public ResponseEntity<Movie> getMovieById(@PathVariable int index) {
    if (index >= movies.size()) {
      return ResponseEntity.badRequest().build();
    }

    Movie movie = movies.get(index);

    if (movie == null) {
      return ResponseEntity.notFound().build();
    }

    return ResponseEntity.ok(movie);
  }

  @PostMapping("/movies")
  public ResponseEntity<Movie> addMovie(@RequestBody Movie movie) {
    if (movie.getTitle() == null) {
      return ResponseEntity.badRequest().build();
    }

    movies.add(movie);

    return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{index}")
            .buildAndExpand(movies.size() - 1).toUri()).body(movie);
  }
}
