package com.pitang.projectPitang.utils.specifications;

import com.pitang.projectPitang.models.Person;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class PersonSpecification {

  public static Specification<Person> searchPerson(String name){

    return new Specification<Person>() {
      @Override
      public Predicate toPredicate(Root<Person> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

        Predicate queryResult = null;
        if(name!=null){
          queryResult = cb.and(cb.like(cb.upper(root.get("name")),"%"+name.toUpperCase()+"%"));
          return queryResult;
        }

        return  queryResult;
     }
    };
  }
}
