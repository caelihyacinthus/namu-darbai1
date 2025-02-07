package lt.caeli.muchEvenBetterMovie.dto;

import lt.caeli.muchEvenBetterMovie.model.Movie;

import java.util.List;

public class MovieMapper {
  public static List<MovieDTO> toMovieDTOList(List<Movie> movies) {
    return movies.stream().map(m -> new MovieDTO(m.getId(), m.getTitle(), m.getDirector(), m.getScreenings())).toList();
  }

  public static MovieDTO toMovieDTO(Movie movie) {
    return new MovieDTO(movie.getId(), movie.getTitle(), movie.getDirector(), movie.getScreenings());
  }

  public static Movie toMovie(MovieDTO movieDTO) {
    Movie movie = new Movie();
    movie.setTitle(movieDTO.title());
    movie.setDirector(movieDTO.director());
    movie.setScreenings(movieDTO.screenings());
    return movie;
  }

  public static void updateMovieWithMovieDTO(Movie movie, MovieDTO movieDTO) {
    movie.setTitle(movieDTO.title());
    movie.setDirector(movieDTO.director());
    movie.setScreenings(movieDTO.screenings());
  }
}
