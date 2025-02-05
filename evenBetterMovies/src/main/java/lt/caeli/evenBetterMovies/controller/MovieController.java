package lt.caeli.evenBetterMovies.controller;

import lt.caeli.evenBetterMovies.model.Movie;
import lt.caeli.evenBetterMovies.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class MovieController {
  private MovieService movieService;

  @Autowired
  public MovieController(MovieService movieService) {
    this.movieService = movieService;
  }

  @GetMapping("/movies")
  public ResponseEntity<List<Movie>> getMovies() {
    return ResponseEntity.ok(movieService.findAllMovies());
  }

  @GetMapping("/movies/{id}")
  public ResponseEntity<Movie> getMovie(@PathVariable long id) {
    Optional<Movie> movieOptional = movieService.findMovieById(id);
    return movieOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PostMapping("/movies")
  public ResponseEntity<?> saveMovie(@RequestBody Movie movie) {
    if (movie.getTitle().isBlank() || movie.getTitle().isEmpty()) {
      return ResponseEntity.badRequest().body("movie title empty");
    }
    if (movie.getDirector().isBlank() || movie.getDirector().isEmpty()) {
      return ResponseEntity.badRequest().body("movie director empty");
    }
    if (movieService.existsMovieByTitleAndDirector(movie.getTitle(), movie.getDirector())) {
      return ResponseEntity.badRequest().body("movie already exists");
    }

    Movie movieSaved = movieService.saveMovie(movie);
    return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                    .buildAndExpand(movieSaved.getId())
                    .toUri())
            .body(movieSaved);
  }

  @PutMapping("/movies/{id}")
  public ResponseEntity<?> updateMovie(@PathVariable long id, @RequestBody Movie movie) {
    if (movie.getTitle().isBlank() || movie.getTitle().isEmpty()) {
      return ResponseEntity.badRequest().body("movie title empty");
    }
    if (movie.getDirector().isBlank() || movie.getDirector().isEmpty()) {
      return ResponseEntity.badRequest().body("movie director empty");
    }

    if (movieService.existsMovieById(id)) {
      Movie movieOld = movieService.findMovieById(id).get();

      movieOld.setDirector(movie.getDirector());
      movieOld.setTitle(movie.getTitle());
      movieOld.setScreenings(movie.getScreenings());

      return ResponseEntity.ok(movieService.saveMovie(movieOld));
    } else {
      if (movieService.existsMovieByTitleAndDirector(movie.getTitle(), movie.getDirector())) {
        return ResponseEntity.badRequest().body("movie already exists with such director and title");
      }

      Movie movieNew = movieService.saveMovie(movie);

      return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().replacePath("movie/{id}")
                      .buildAndExpand(movieNew.getId())
                      .toUri())
              .body(movieNew);
    }
  }

  @DeleteMapping("/movies/{id}")
  public ResponseEntity<Void> deleteMovie(@PathVariable long id) {
    if (movieService.existsMovieById(id)) {
      movieService.deleteMovieById(id);
      return ResponseEntity.noContent().build();
    } else {
      return ResponseEntity.notFound().build();
    }
  }
}