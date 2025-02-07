package lt.caeli.muchEvenBetterMovie.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lt.caeli.muchEvenBetterMovie.validation.TheaterName;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "screenings")
public class Screening {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  @TheaterName
  private String theater;
  private LocalDate date;
  private LocalTime time;

  public Screening() {
  }

  public Screening(String theater, LocalDate date, LocalTime time) {
    this.theater = theater;
    this.date = date;
    this.time = time;
  }

  public long getId() {
    return id;
  }

  public String getTheater() {
    return theater;
  }

  public void setTheater(String theater) {
    this.theater = theater;
  }

  public LocalDate getDate() {
    return date;
  }

  public void setDate(LocalDate date) {
    this.date = date;
  }

  public LocalTime getTime() {
    return time;
  }

  public void setTime(LocalTime time) {
    this.time = time;
  }
}