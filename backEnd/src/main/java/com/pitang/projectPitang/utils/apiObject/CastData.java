package com.pitang.projectPitang.utils.apiObject;

import java.io.Serializable;
import java.util.List;

public class CastData implements Serializable {

  private Integer id;
  private List<Object> cast;
  private List<Object> crew;

  public CastData(){
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public List<Object> getCast() {
    return cast;
  }

  public void setCast(List<Object> cast) {
    this.cast = cast;
  }

  public List<Object> getCrew() {
    return crew;
  }

  public void setCrew(List<Object> crew) {
    this.crew = crew;
  }
}
