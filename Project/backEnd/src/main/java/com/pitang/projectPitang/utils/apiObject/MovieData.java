package com.pitang.projectPitang.utils.apiObject;

import com.pitang.projectPitang.models.dtosAPI.MovieDTO;

import java.io.Serializable;
import java.util.List;

public class MovieData implements Serializable {

  private Integer page;
  private Integer total_results;
  private Integer total_pages;
  private List<MovieDTO> results;


  public MovieData(){

  }

  public Integer getPage() {
    return page;
  }

  public void setPage(Integer page) {
    this.page = page;
  }

  public Integer getTotal_results() {
    return total_results;
  }

  public void setTotal_results(Integer total_results) {
    this.total_results = total_results;
  }

  public Integer getTotal_pages() {
    return total_pages;
  }

  public void setTotal_pages(Integer total_pages) {
    this.total_pages = total_pages;
  }

  public List<MovieDTO> getResults() {
    return results;
  }

  public void setResults(List<MovieDTO> results) {
    this.results = results;
  }
}
