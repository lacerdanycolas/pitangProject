package com.pitang.projectPitang.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreType;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "tb_castTv")
public class CastTv {

  @Id
  private Integer id;


  @ManyToMany(mappedBy = "castTv", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  private List<Person> persons;

  @JsonIgnore
  @OneToOne(mappedBy = "cast",cascade = {CascadeType.MERGE, CascadeType.PERSIST})
  private TvSerie tvSerie;

  public CastTv(){

  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public List<Person> getPersons() {
    return persons;
  }

  public void setPersons(List<Person> persons) {
    this.persons = persons;
  }

  public TvSerie getTvSerie() {
    return tvSerie;
  }

  public void setTvSerie(TvSerie tvSerie) {
    this.tvSerie = tvSerie;
  }
}
