package com.pitang.projectPitang.repository;

import com.pitang.projectPitang.models.Genre;
import org.springframework.data.jpa.repository.JpaRepository;


@org.springframework.stereotype.Repository
public interface GenreRepository extends JpaRepository<Genre, Integer> {
}
