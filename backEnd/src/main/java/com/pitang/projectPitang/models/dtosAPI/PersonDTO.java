package com.pitang.projectPitang.models.dtosAPI;

import com.pitang.projectPitang.utils.Gender;

import java.io.Serializable;
import java.util.List;

public class PersonDTO implements Serializable {

  private String birthday;
  private String known_for_department;
  private String deathday;
  private Integer id;
  private String name;
  private List<String> also_known_as;
  private Gender gender;
  private String biography;
  private Double popularity;
  private String place_of_birth;
  private String profile_path;
  private boolean adult;
  private String imdb_id;
  private String homepage;

  public PersonDTO(){

  }

  public String getBirthday() {
    return birthday;
  }

  public void setBirthday(String birthday) {
    this.birthday = birthday;
  }

  public String getKnown_for_department() {
    return known_for_department;
  }

  public void setKnown_for_department(String known_for_department) {
    this.known_for_department = known_for_department;
  }

  public String getDeathday() {
    return deathday;
  }

  public void setDeathday(String deathday) {
    this.deathday = deathday;
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

  public List<String> getAlso_known_as() {
    return also_known_as;
  }

  public void setAlso_known_as(List<String> also_known_as) {
    this.also_known_as = also_known_as;
  }

  public Gender getGender() {
    return gender;
  }

  public void setGender(Gender gender) {
    this.gender = gender;
  }

  public String getBiography() {
    return biography;
  }

  public void setBiography(String biography) {
    this.biography = biography;
  }

  public Double getPopularity() {
    return popularity;
  }

  public void setPopularity(Double popularity) {
    this.popularity = popularity;
  }

  public String getPlace_of_birth() {
    return place_of_birth;
  }

  public void setPlace_of_birth(String place_of_birth) {
    this.place_of_birth = place_of_birth;
  }

  public String getProfile_path() {
    return profile_path;
  }

  public void setProfile_path(String profile_path) {
    this.profile_path = profile_path;
  }

  public boolean isAdult() {
    return adult;
  }

  public void setAdult(boolean adult) {
    this.adult = adult;
  }

  public String getImdb_id() {
    return imdb_id;
  }

  public void setImdb_id(String imdb_id) {
    this.imdb_id = imdb_id;
  }

  public String getHomepage() {
    return homepage;
  }

  public void setHomepage(String homepage) {
    this.homepage = homepage;
  }
}
