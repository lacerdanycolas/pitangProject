package com.pitang.projectPitang.repository;

import com.pitang.projectPitang.models.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

@org.springframework.stereotype.Repository
public interface MovieRepository extends JpaRepository<Movie, Integer>, JpaSpecificationExecutor<Movie> {

   Movie findByTitleIgnoreCase(String title);
   Page<Movie> findByTitleContainingIgnoreCase(Pageable pageable, String title);
}
