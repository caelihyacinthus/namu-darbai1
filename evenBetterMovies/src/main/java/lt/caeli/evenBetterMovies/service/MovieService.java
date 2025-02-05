package lt.caeli.evenBetterMovies.service;

import lt.caeli.evenBetterMovies.model.Movie;
import lt.caeli.evenBetterMovies.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {
  public final MovieRepository movieRepository;

  @Autowired
  public MovieService(MovieRepository movieRepository) {
    this.movieRepository = movieRepository;
  }

  public List<Movie> findAllMovies() {
    return movieRepository.findAll();
  }

  public Optional<Movie> findMovieById(long id) {
    return movieRepository.findById(id);
  }

  public Movie saveMovie(Movie movie) {
    return movieRepository.save(movie);
  }

  public boolean existsMovieById(Long id) {
    return movieRepository.existsById(id);
  }

  public void deleteMovieById(long id) {
    movieRepository.deleteById(id);
  }

  public boolean existsMovieByTitle(String title) {
    return movieRepository.existsByTitle(title);
  }

  public boolean existsMovieByDirector(String director) {
    return movieRepository.existsByDirector(director);
  }

  public boolean existsMovieByTitleAndDirector(String title, String director) {
    return movieRepository.existsByTitleAndDirector(title, director);
  }

}