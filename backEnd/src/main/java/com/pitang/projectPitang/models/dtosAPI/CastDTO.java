package com.pitang.projectPitang.models.dtosAPI;

import java.util.List;

public class CastDTO {

  private Integer id;
  private List<PersonCastDTO> cast;
  private List<CrewCastDTO> crew;

  public CastDTO(){

  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public List<PersonCastDTO> getCast() {
    return cast;
  }

  public void setCast(List<PersonCastDTO> cast) {
    this.cast = cast;
  }

  public List<CrewCastDTO> getCrew() {
    return crew;
  }

  public void setCrew(List<CrewCastDTO> crew) {
    this.crew = crew;
  }
}
