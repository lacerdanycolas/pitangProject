package com.pitang.projectPitang.repository;

import com.pitang.projectPitang.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

@org.springframework.stereotype.Repository
public interface PersonRepository extends JpaRepository<Person,Integer>, JpaSpecificationExecutor<Person> {

  List<Person> findByNameIgnoreCase(String name);
}
