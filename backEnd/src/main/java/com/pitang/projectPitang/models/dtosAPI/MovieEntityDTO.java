package com.pitang.projectPitang.models.dtosAPI;

import java.io.Serializable;
import java.util.List;

public class MovieEntityDTO implements Serializable {

  private boolean adult;
  private String backdrop_path;
  private Object belongs_to_collection;
  private Integer budget;
  private List<GenreDTO> genres;
  private String homepage;
  private Integer id;
  private String imdb_id;
  private String original_language;
  private String original_title;
  private String overview;
  private Double popularity;
  private String poster_path;
  private List<Object> production_companies;
  private List<Object> production_countries;
  private String release_date;
  private Integer revenue;
  private Integer runtime;
  private List<Object> spoken_languages;
  private String status;
  private String tagline;
  private String title;
  private boolean video;
  private Double vote_average;
  private Integer vote_count;


  public MovieEntityDTO(){

  }

  public boolean isAdult() {
    return adult;
  }

  public void setAdult(boolean adult) {
    this.adult = adult;
  }

  public String getBackdrop_path() {
    return backdrop_path;
  }

  public void setBackdrop_path(String backdrop_path) {
    this.backdrop_path = backdrop_path;
  }

  public Object getBelongs_to_collection() {
    return belongs_to_collection;
  }

  public void setBelongs_to_collection(Object belongs_to_collection) {
    this.belongs_to_collection = belongs_to_collection;
  }

  public Integer getBudget() {
    return budget;
  }

  public void setBudget(Integer budget) {
    this.budget = budget;
  }

  public List<GenreDTO> getGenres() {
    return genres;
  }

  public void setGenres(List<GenreDTO> genres) {
    this.genres = genres;
  }

  public String getHomepage() {
    return homepage;
  }

  public void setHomepage(String homepage) {
    this.homepage = homepage;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getImdb_id() {
    return imdb_id;
  }

  public void setImdb_id(String imdb_id) {
    this.imdb_id = imdb_id;
  }

  public String getOriginal_language() {
    return original_language;
  }

  public void setOriginal_language(String original_language) {
    this.original_language = original_language;
  }

  public String getOriginal_title() {
    return original_title;
  }

  public void setOriginal_title(String original_title) {
    this.original_title = original_title;
  }

  public String getOverview() {
    return overview;
  }

  public void setOverview(String overview) {
    this.overview = overview;
  }

  public Double getPopularity() {
    return popularity;
  }

  public void setPopularity(Double popularity) {
    this.popularity = popularity;
  }

  public String getPoster_path() {
    return poster_path;
  }

  public void setPoster_path(String poster_path) {
    this.poster_path = poster_path;
  }

  public List<Object> getProduction_companies() {
    return production_companies;
  }

  public void setProduction_companies(List<Object> production_companies) {
    this.production_companies = production_companies;
  }

  public List<Object> getProduction_countries() {
    return production_countries;
  }

  public void setProduction_countries(List<Object> production_countries) {
    this.production_countries = production_countries;
  }

  public String getRelease_date() {
    return release_date;
  }

  public void setRelease_date(String release_date) {
    this.release_date = release_date;
  }

  public Integer getRevenue() {
    return revenue;
  }

  public void setRevenue(Integer revenue) {
    this.revenue = revenue;
  }

  public Integer getRuntime() {
    return runtime;
  }

  public void setRuntime(Integer runtime) {
    this.runtime = runtime;
  }

  public List<Object> getSpoken_languages() {
    return spoken_languages;
  }

  public void setSpoken_languages(List<Object> spoken_languages) {
    this.spoken_languages = spoken_languages;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getTagline() {
    return tagline;
  }

  public void setTagline(String tagline) {
    this.tagline = tagline;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public boolean isVideo() {
    return video;
  }

  public void setVideo(boolean video) {
    this.video = video;
  }

  public Double getVote_average() {
    return vote_average;
  }

  public void setVote_average(Double vote_average) {
    this.vote_average = vote_average;
  }

  public Integer getVote_count() {
    return vote_count;
  }

  public void setVote_count(Integer vote_count) {
    this.vote_count = vote_count;
  }
}
