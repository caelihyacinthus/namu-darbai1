package lt.caeli.movies.controller;

import lt.caeli.movies.model.Movie;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class MovieController {
  private List<Movie> movies = new ArrayList<>(List.of(new Movie("hell", "pupupu"), new Movie("earth", "uiuouo"), new Movie("heaven", "oioioi")));

  @GetMapping("/movies")
  public ResponseEntity<List<Movie>> getMovies() {
    return ResponseEntity.ok(movies);
  }

  @GetMapping("/movies/{index}")
  public ResponseEntity<Movie> getMovieById(@PathVariable int index) {
    if (index >= movies.size()) {
      return ResponseEntity.notFound().build();
    }

    Movie movie = movies.get(index);

    return ResponseEntity.ok(movie);
  }

  @PostMapping("/movies")
  public ResponseEntity<?> addMovie(@RequestBody Movie movie) {
    if (movie.getTitle() == null || movie.getDirector() == null) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("movie title empty");
    }

    movies.add(movie);

    return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{index}")
            .buildAndExpand(movies.size() - 1).toUri()).body(movie);
  }

  @GetMapping("/movies/search")
  public ResponseEntity<List<Movie>> searchByTitle(@RequestParam String title) {
    List<Movie> movieList = movies.stream().filter(m -> m.getTitle().toLowerCase().contains(title.toLowerCase())).toList();

    return ResponseEntity.ok(movieList);
  }
}
