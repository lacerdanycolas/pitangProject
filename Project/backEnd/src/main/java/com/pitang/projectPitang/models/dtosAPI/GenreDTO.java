package com.pitang.projectPitang.models.dtosAPI;

import java.io.Serializable;

public class GenreDTO implements Serializable {

  private Integer id;
  private String name;

  public GenreDTO(){

  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
