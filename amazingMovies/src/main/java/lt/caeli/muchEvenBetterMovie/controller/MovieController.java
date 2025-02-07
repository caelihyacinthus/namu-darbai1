package lt.caeli.muchEvenBetterMovie.controller;

import jakarta.validation.Valid;
import lt.caeli.muchEvenBetterMovie.dto.MovieDTO;
import lt.caeli.muchEvenBetterMovie.dto.MovieMapper;
import lt.caeli.muchEvenBetterMovie.model.Movie;
import lt.caeli.muchEvenBetterMovie.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
  public ResponseEntity<List<MovieDTO>> getMovies() {
    return ResponseEntity.ok(MovieMapper.toMovieDTOList(movieService.findAllMovies()));
  }

  @GetMapping("/movies/{id}")
  public ResponseEntity<MovieDTO> getMovie(@PathVariable long id) {
    Optional<Movie> movieOptional = movieService.findMovieById(id);
    return movieOptional.map(movie -> ResponseEntity.ok(MovieMapper.toMovieDTO(movie))).orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PostMapping("/movies")
  public ResponseEntity<?> saveMovie(@Valid @RequestBody MovieDTO movieDTO) {
    if (movieService.existsMovieByTitleAndDirector(movieDTO.title(), movieDTO.director())) {
      Map<String, String> badResponse = new HashMap<>();
      badResponse.put("error", "movie already exists with such director and title");
      return ResponseEntity.badRequest().body(badResponse);
    }

    Movie movieSaved = movieService.saveMovie(MovieMapper.toMovie(movieDTO));
    return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                    .buildAndExpand(movieSaved.getId())
                    .toUri())
            .body(MovieMapper.toMovieDTO(movieSaved));
  }

  @PutMapping("/movies/{id}")
  public ResponseEntity<?> updateMovie(@PathVariable long id, @Valid @RequestBody MovieDTO movieDTO) {
    if (movieService.existsMovieById(id)) {
      Movie movieOld = movieService.findMovieById(id).get();

      MovieMapper.updateMovieWithMovieDTO(movieOld, movieDTO);

      return ResponseEntity.ok(movieService.saveMovie(movieOld));
    } else {
      if (movieService.existsMovieByTitleAndDirector(movieDTO.title(), movieDTO.director())) {
        Map<String, String> badResponse = new HashMap<>();
        badResponse.put("error", "movie already exists with such director and title");
        return ResponseEntity.badRequest().body(badResponse);
      }

      Movie movieNew = movieService.saveMovie(MovieMapper.toMovie(movieDTO));

      return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().replacePath("movie/{id}")
                      .buildAndExpand(movieNew.getId())
                      .toUri())
              .body(MovieMapper.toMovieDTO(movieNew));
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

  @GetMapping("movies/pagination")
  public ResponseEntity<Page<Movie>> getMoviePage(@RequestParam int page,
                                                  @RequestParam int size,
                                                  @RequestParam(required = false) String sort) {
    return ResponseEntity.ok(movieService.findAllMoviesPage(page, size, sort));
  }
}