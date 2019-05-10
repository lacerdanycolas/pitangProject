package com.pitang.projectPitang.models.dtosAPI;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "tb_crewCastEntity")
public class CrewCastEntity {

  private String credit_id;
  private String department;
  private Integer gender;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;
  private String job;
  private String name;
  private String profile_path;

  public CrewCastEntity(){

  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    CrewCastEntity that = (CrewCastEntity) o;
    return Objects.equals(credit_id, that.credit_id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(credit_id);
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
