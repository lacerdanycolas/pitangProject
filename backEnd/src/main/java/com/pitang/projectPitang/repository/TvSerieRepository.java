package com.pitang.projectPitang.repository;

import com.pitang.projectPitang.models.TvSerie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

@org.springframework.stereotype.Repository
public interface TvSerieRepository extends JpaRepository<TvSerie, Integer>, JpaSpecificationExecutor<TvSerie> {

  TvSerie findByNameIgnoreCase(String title);
}
