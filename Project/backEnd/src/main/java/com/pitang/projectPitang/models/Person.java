package com.pitang.projectPitang.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.pitang.projectPitang.utils.Gender;
import jdk.nashorn.internal.objects.annotations.Getter;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "tb_person")
public class Person {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  private String name;
  private String place_of_birth;
  private String profile_path;

  @Enumerated
  private Gender gender;


  @JsonIgnore
  @ManyToMany(cascade={CascadeType.PERSIST, CascadeType.MERGE})
  @JoinTable(
    name = "cast_person",
    joinColumns = @JoinColumn(name = "person_id"),
    inverseJoinColumns = @JoinColumn(name = "cast_id")
  )
  private List<Cast> cast;


  @JsonIgnore
  @ManyToMany(cascade={CascadeType.PERSIST, CascadeType.MERGE})
  @JoinTable(
    name = "castTv_person",
    joinColumns = @JoinColumn(name = "person_id"),
    inverseJoinColumns = @JoinColumn(name = "castTv_id")
  )
  private List<CastTv> castTv;

  public Person(){

  }

  public Person(String name, Gender gender, String place_of_birth) {
    this.name = name;
    this.gender = gender;
    this.place_of_birth = place_of_birth;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Person person = (Person) o;
    return Objects.equals(id, person.id);
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

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPlace_of_birth() {
    return place_of_birth;
  }

  public void setPlace_of_birth(String place_of_birth) {
    this.place_of_birth = place_of_birth;
  }

  public Gender getGender() {
    return gender;
  }

  public void setGender(Gender gender) {
    this.gender = gender;
  }

  public List<Cast> getCast() {
    return cast;
  }

  public void setCast(List<Cast> cast) {
    this.cast = cast;
  }

  public List<CastTv> getCastTv() {
    return castTv;
  }

  public void setCastTv(List<CastTv> castTv) {
    this.castTv = castTv;
  }

  public String getProfile_path() {
    return profile_path;
  }

  public void setProfile_path(String profile_path) {
    this.profile_path = profile_path;
  }


}
