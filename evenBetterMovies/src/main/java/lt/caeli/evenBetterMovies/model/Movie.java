package lt.caeli.evenBetterMovies.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "movies")
public class Movie {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String title;
  private String director;
  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "movie_id")
  private List<Screening> screenings;

  public Movie() {
  }

  public Movie(String title, String director) {
    this.title = title;
    this.director = director;
  }

  public Long getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDirector() {
    return director;
  }

  public void setDirector(String director) {
    this.director = director;
  }

  public List<Screening> getScreenings() {
    return screenings;
  }

  public void setScreenings(List<Screening> screenings) {
    this.screenings = screenings;
  }
}
