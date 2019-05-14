package com.pitang.projectPitang.utils.apiObject;

import com.pitang.projectPitang.models.dtosAPI.GenreDTO;

import java.io.Serializable;
import java.util.List;

public class GenreData implements Serializable {

  private List<GenreDTO> genres;

  public GenreData(){

  }

  public List<GenreDTO> getGenres() {
    return genres;
  }

  public void setGenres(List<GenreDTO> genres) {
    this.genres = genres;
  }
}
