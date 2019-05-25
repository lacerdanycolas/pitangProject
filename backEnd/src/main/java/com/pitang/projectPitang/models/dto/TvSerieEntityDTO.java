package com.pitang.projectPitang.models.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TvSerieEntityDTO {

  private String name;

  private String overview;

  private String original_language;
  private String first_air_date;

  private Integer episode_run_time;

  private String backdrop_path;

  private List<String> original_countries;
  private List<Integer> genres_id;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
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

  public String getFirst_air_date() {
    return first_air_date;
  }

  public void setFirst_air_date(String first_air_date) {
    this.first_air_date = first_air_date;
  }

  public Integer getEpisode_run_time() {
    return episode_run_time;
  }

  public void setEpisode_run_time(Integer episode_run_time) {
    this.episode_run_time = episode_run_time;
  }

  public String getBackdrop_path() {
    return backdrop_path;
  }

  public void setBackdrop_path(String backdrop_path) {
    this.backdrop_path = backdrop_path;
  }

  public List<String> getOriginal_countries() {
    return original_countries;
  }

  public void setOriginal_countries(List<String> original_countries) {
    this.original_countries = original_countries;
  }

  public List<Integer> getGenres_id() {
    return genres_id;
  }

  public void setGenres_id(List<Integer> genres_id) {
    this.genres_id = genres_id;
  }
}
