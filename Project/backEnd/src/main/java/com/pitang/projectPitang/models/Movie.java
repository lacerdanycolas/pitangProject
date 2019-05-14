package com.pitang.projectPitang.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "tb_movie")
public class Movie {


  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;
  private String title;

  @Column(length = 1500)
  private String overview;
  private String original_language;
  private String release_date;
  private Integer runtime;

  private String backdrop_path;

  @ElementCollection
  @CollectionTable(name="movie_genres", joinColumns=@JoinColumn(name="movie_id"))
  private List<Integer> genres_id;

  @JsonIgnore
  @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
  @JoinColumn(name = "cast_id")
  private Cast cast;


  public Movie() {
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Movie movie = (Movie) o;
    return Objects.equals(id, movie.id) &&
      Objects.equals(title, movie.title) &&
      Objects.equals(overview, movie.overview) &&
      Objects.equals(original_language, movie.original_language) &&
      Objects.equals(release_date, movie.release_date);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, title, overview, original_language, release_date);
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getOverview() {
    return overview;
  }

  public void setOverview(String overview) {
    this.overview = overview;
  }

  public String getOriginal_language() {
    return original_language;
  }

  public void setOriginal_language(String original_language) {
    this.original_language = original_language;
  }

  public String getRelease_date() {
    return release_date;
  }

  public void setRelease_date(String release_date) {
    this.release_date = release_date;
  }

  public Integer getRuntime() {
    return runtime;
  }

  public void setRuntime(Integer runtime) {
    this.runtime = runtime;
  }

  public List<Integer> getGenres_id() {
    return genres_id;
  }

  public void setGenres_id(List<Integer> genres_id) {
    this.genres_id = genres_id;
  }

  public Cast getCast() {
    return cast;
  }

  public void setCast(Cast cast) {
    this.cast = cast;
  }

  public String getBackdrop_path() {
    return backdrop_path;
  }

  public void setBackdrop_path(String backdrop_path) {
    this.backdrop_path = backdrop_path;
  }
}

