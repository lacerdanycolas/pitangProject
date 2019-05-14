package com.pitang.projectPitang.repository;


import com.pitang.projectPitang.models.Cast;
import org.springframework.data.jpa.repository.JpaRepository;

@org.springframework.stereotype.Repository
public interface CastRepository extends JpaRepository<Cast,Integer> {
}
