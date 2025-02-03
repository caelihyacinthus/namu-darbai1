package lt.caeli.movies.controller;

import com.fasterxml.jackson.databind.type.ResolvedRecursiveType;
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

  @GetMapping("/movies/search")
  public ResponseEntity<List<Movie>> searchByTitle(@RequestParam String title) {
    List<Movie> movieList = movies.stream().filter(m -> m.getTitle().toLowerCase().contains(title.toLowerCase())).toList();

    return ResponseEntity.ok(movieList);
  }

  @PostMapping("/movies")
  public ResponseEntity<?> addMovie(@RequestBody Movie movie) {
    if (movie.getTitle() == null || movie.getDirector() == null) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("movie title or director empty");
    }

    movies.add(movie);

    return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path("/{index}")
                    .buildAndExpand(movies.size() - 1)
                    .toUri())
            .body(movie);
  }

  @PutMapping("/movies/{index}")
  public ResponseEntity<?> updateMovie(@PathVariable int index, @RequestBody Movie movie) {
    if (movie.getTitle() == null || movie.getTitle().isEmpty()) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("movie title empty");
    } else if (movie.getDirector() == null || movie.getDirector().isEmpty()) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("director name empty");
    } else if (index < movies.size()) {
      movies.get(index).setTitle(movie.getTitle());
      movies.get(index).setDirector(movie.getDirector());
      return ResponseEntity.ok(movie);
    } else {
      movies.add(movie);
      return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path("/{index}")
                      .buildAndExpand(movies.size() - 1)
                      .toUri())
              .body(movie);
    }
  }

  @DeleteMapping("/movies/{index}")
  public ResponseEntity<?> deleteMovie(@PathVariable int index) {
    if (index >= movies.size()) {
      return ResponseEntity.notFound().build();
    } else {
      movies.remove(index);
      return ResponseEntity.noContent().build();
    }
  }


}
