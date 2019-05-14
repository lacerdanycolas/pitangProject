package com.pitang.projectPitang.models.dtosAPI;

import java.io.Serializable;
import java.util.List;

public class TvDTO implements Serializable {

  private int vote_count;
  private int id;
  private boolean video;
  private int vote_average;
  private String title;
  private double popularity;
  private String poster_path;
  private String original_language;
  private String original_title;
  private List<Integer> genre_ids;
  private String backdrop_path;
  private boolean adult;
  private String overview;
  private String release_date;

  public TvDTO(){

  }

  public int getVote_count() {
    return vote_count;
  }

  public void setVote_count(int vote_count) {
    this.vote_count = vote_count;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public boolean isVideo() {
    return video;
  }

  public void setVideo(boolean video) {
    this.video = video;
  }

  public int getVote_average() {
    return vote_average;
  }

  public void setVote_average(int vote_average) {
    this.vote_average = vote_average;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public double getPopularity() {
    return popularity;
  }

  public void setPopularity(double popularity) {
    this.popularity = popularity;
  }

  public String getPoster_path() {
    return poster_path;
  }

  public void setPoster_path(String poster_path) {
    this.poster_path = poster_path;
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

  public List<Integer> getGenre_ids() {
    return genre_ids;
  }

  public void setGenre_ids(List<Integer> genre_ids) {
    this.genre_ids = genre_ids;
  }

  public String getBackdrop_path() {
    return backdrop_path;
  }

  public void setBackdrop_path(String backdrop_path) {
    this.backdrop_path = backdrop_path;
  }

  public boolean isAdult() {
    return adult;
  }

  public void setAdult(boolean adult) {
    this.adult = adult;
  }

  public String getOverview() {
    return overview;
  }

  public void setOverview(String overview) {
    this.overview = overview;
  }

  public String getRelease_date() {
    return release_date;
  }

  public void setRelease_date(String release_date) {
    this.release_date = release_date;
  }
}
