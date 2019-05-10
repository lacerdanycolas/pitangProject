package com.pitang.projectPitang.utils;

import com.pitang.projectPitang.models.dtosAPI.MovieDTO;

import java.util.List;

public class MovieFactory {

  private List<MovieDTO> movies;

  public MovieFactory(){

  }

  public List<MovieDTO> getMovies() {
    return movies;
  }

  public void setMovies(List<MovieDTO> movies) {
    this.movies = movies;
  }
}
