package com.pitang.projectPitang.models.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pitang.projectPitang.models.Person;
import com.pitang.projectPitang.utils.Gender;
import jdk.nashorn.internal.objects.annotations.Getter;

import javax.persistence.Enumerated;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PersonEntityDTO {

  private String name;
  private String place_of_birth;
  private String profile_path;
  private Gender gender;

  public Person transformaParaObjeto(){
    return new Person(name,gender,place_of_birth,profile_path);
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

  public String getProfile_path() {
    return profile_path;
  }

  public void setProfile_path(String profile_path) {
    this.profile_path = profile_path;
  }
}
