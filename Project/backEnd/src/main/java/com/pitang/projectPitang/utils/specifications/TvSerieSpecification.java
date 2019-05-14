package com.pitang.projectPitang.utils.specifications;

import com.pitang.projectPitang.models.TvSerie;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class TvSerieSpecification {

  public static Specification<TvSerie> searchSerie(String name, String first_air_date, String original_language){

    return new Specification<TvSerie>() {
      @Override
      public Predicate toPredicate(Root<TvSerie> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

        Predicate queryResult = null;
        if(name!=null && first_air_date!= null && original_language!=null){
          queryResult = cb.and(cb.like(cb.upper(root.get("name")),"%"+name.toUpperCase()+"%"), cb.like(root.get("original_language"), original_language),
            cb.like(root.get("first_air_date"),  first_air_date + "%"));
          return queryResult;
        } else if(name!=null && first_air_date!=null){
          queryResult = cb.and(cb.like(cb.upper(root.get("name")),"%"+name.toUpperCase()+"%"),
            cb.like(root.get("first_air_date"),  first_air_date + "%"));
          return queryResult;
        } else if(name!=null && original_language!=null){
          queryResult = cb.and(cb.like(cb.upper(root.get("name")),"%"+name.toUpperCase()+"%"), cb.like(root.get("original_language"), original_language));
          return queryResult;
        } else if(original_language!= null && first_air_date!=null){
          queryResult = cb.and(cb.like(root.get("original_language"), original_language),cb.like(root.get("first_air_date"),  first_air_date + "%"));
          return queryResult;
        }else if(first_air_date!= null){
          queryResult = cb.and(cb.like(root.get("first_air_date"),  first_air_date + "%"));
          return queryResult;
        }else if(name != null){
          queryResult = cb.and(cb.like(cb.upper(root.get("name")),"%"+name.toUpperCase()+"%"));
          return queryResult;
        }else if(original_language!=null){
          queryResult =  cb.and(cb.like(root.get("original_language"), original_language));
          return queryResult;
        }

        return  queryResult;
      }
    };

  }
}
