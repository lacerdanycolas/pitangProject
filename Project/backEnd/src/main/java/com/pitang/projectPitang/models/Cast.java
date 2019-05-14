package com.pitang.projectPitang.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreType;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "tb_cast")
public class Cast {

  @Id
  private Integer id;

  @ManyToMany(mappedBy =  "cast",cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
  private List<Person> persons;

  @JsonIgnore
  @OneToOne(mappedBy = "cast",cascade = {CascadeType.MERGE, CascadeType.PERSIST})
  private Movie movie;


  public Cast(){

  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Cast cast = (Cast) o;
    return Objects.equals(id, cast.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
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

  public Movie getMovie() {
    return movie;
  }

  public void setMovie(Movie movie) {
    this.movie = movie;
  }
}
