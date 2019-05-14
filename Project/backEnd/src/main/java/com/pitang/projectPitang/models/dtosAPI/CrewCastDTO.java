package com.pitang.projectPitang.models.dtosAPI;

import java.io.Serializable;

public class CrewCastDTO extends Object implements Serializable {

  private String credit_id;
  private String department;
  private Integer gender;
  private Integer id;
  private String job;
  private String name;
  private String profile_path;

  public CrewCastDTO(){

  }

  public String getCredit_id() {
    return credit_id;
  }

  public void setCredit_id(String credit_id) {
    this.credit_id = credit_id;
  }

  public String getDepartment() {
    return department;
  }

  public void setDepartment(String department) {
    this.department = department;
  }

  public Integer getGender() {
    return gender;
  }

  public void setGender(Integer gender) {
    this.gender = gender;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getJob() {
    return job;
  }

  public void setJob(String job) {
    this.job = job;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getProfile_path() {
    return profile_path;
  }

  public void setProfile_path(String profile_path) {
    this.profile_path = profile_path;
  }
}
